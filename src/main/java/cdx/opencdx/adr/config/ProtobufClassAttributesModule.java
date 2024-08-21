/*
 * Copyright 2024 Safe Health Systems, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cdx.opencdx.adr.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.*;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Message;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Returns a map of the FieldDescriptors for the fields of the given class.
 *
 * @return A map of field names to FieldDescriptor objects.
 */
@Slf4j
public class ProtobufClassAttributesModule extends Module {

    /**
     * An implementation of the {@link AnnotationIntrospector}
     * interface that performs no introspection or annotation processing.
     * This implementation is used for specific requirements in the application.
     *
     * <p>
     * The {@code NopAnnotationIntrospector} class overrides two methods from the
     * base {@code AnnotationIntrospector} class:
     * </p>
     * <ul>
     *     <li> </li>
     *     <li> </li>
     * </ul>
     *
     * <p>
     * Note that this class is marked as {@code final} to prevent inheritance.
     * </p>
     * <p>
     * Example usage:
     * <pre>{@code
     *     final NopAnnotationIntrospector annotationIntrospector = new NopAnnotationIntrospector() {
     *         // Override methods as required
     *     };
     * }</pre>
     */
    final NopAnnotationIntrospector annotationIntrospector = new NopAnnotationIntrospector() {

        /**
         * Determines the visibility rules for auto-detection of property accessors and fields for the given annotated class.
         * If the class is a protobuf message (or a superclass of a protobuf message), the visibility rules will be modified
         * to have PUBLIC_ONLY getter visibility and ANY field visibility. Otherwise, the visibility rules will be unchanged.
         *
         * @param ac      The annotated class for which to determine the visibility rules.
         * @param checker The original visibility checker object.
         * @return A visibility checker object with modified visibility rules if the class is a protobuf message, otherwise
         *         the original visibility checker object.
         */
        @Override
        public VisibilityChecker<?> findAutoDetectVisibility(AnnotatedClass ac, VisibilityChecker<?> checker) {
            log.trace("ProtobufClassAttributesModule findAutoDetectVisibility");
            if (Message.class.isAssignableFrom(ac.getRawType())) {
                return checker.withGetterVisibility(Visibility.PUBLIC_ONLY).withFieldVisibility(Visibility.ANY);
            }
            return super.findAutoDetectVisibility(ac, checker);
        }

        /**
         * Finds the naming strategy for the given annotated class.
         *
         * @param ac the annotated class
         * @return the naming strategy for the class
         */
        @Override
        public Object findNamingStrategy(AnnotatedClass ac) {
            log.trace("ProtobufClassAttributesModule findNamingStrategy");
            if (!Message.class.isAssignableFrom(ac.getRawType())) {
                return super.findNamingStrategy(ac);
            }

            return new PropertyNamingStrategies.NamingBase() {
                @Override
                public String translate(String propertyName) {
                    if (propertyName.endsWith("_")) {
                        return propertyName.substring(0, propertyName.length() - 1);
                    }
                    return propertyName;
                }
            };
        }
    };
    /**
     * The cache variable is a private final field of type Map.
     * It is used to store field descriptors based on their class and name.
     * The cache is a concurrent hash map, meaning it is thread-safe for multiple threads to access and modify it concurrently.
     * The key of the cache is a Class object, which represents the class of the field described.
     * The value is another map, which maps the field name to its corresponding FieldDescriptor object.
     * The cache variable is declared as final, indicating that its reference cannot be changed once assigned.
     * It is also marked as private, meaning that it can only be accessed within the same class.
     */
    private final Map<Class<?>, Map<String, FieldDescriptor>> cache = new ConcurrentHashMap<>();

    /**
     * Class representing a module for protobuf class attributes.
     */
    public ProtobufClassAttributesModule() {
        log.trace("ProtobufClassAttributesModule created");
    }

    /**
     * Retrieves the name of the module.
     *
     * @return The name of the module as a String.
     */
    @Override
    public String getModuleName() {
        log.trace("ProtobufClassAttributesModule getModuleName");
        return "ProtobufClassAttributesModule";
    }

    /**
     * Returns the version of the ProtobufClassAttributesModule.
     *
     * @return The version of the ProtobufClassAttributesModule.
     */
    @Override
    public Version version() {
        log.trace("ProtobufClassAttributesModule version");
        return VersionUtil.versionFor(getClass());
    }

    /**
     * Sets up the module with the provided setup context.
     *
     * @param context the setup context
     */
    @Override
    public void setupModule(SetupContext context) {
        log.trace("ProtobufClassAttributesModule setupModule");

        context.setClassIntrospector(new ProtobufClassIntrospector());

        context.insertAnnotationIntrospector(annotationIntrospector);
    }

    /**
     * The ProtobufClassIntrospector class is a subclass of BasicClassIntrospector. It provides
     * custom introspection logic for protobuf-based classes during serialization and deserialization.
     */
    class ProtobufClassIntrospector extends BasicClassIntrospector {

        /**
         * Returns the BasicBeanDescription for deserialization based on the provided DeserializationConfig, JavaType, and MixInResolver.
         * If the raw class of the JavaType is assignable from Message, it returns a protobuf-based BeanDescription using the protobufBeanDescription method.
         * Otherwise, it returns the BasicBeanDescription generated by the super method.
         *
         * @param cfg  The DeserializationConfig used for deserialization.
         * @param type The JavaType representing the deserialized type.
         * @param r    The MixInResolver used to resolve mix-ins.
         * @return The BasicBeanDescription for deserialization.
         */
        @Override
        public BasicBeanDescription forDeserialization(DeserializationConfig cfg, JavaType type, MixInResolver r) {
            log.trace("ProtobufClassAttributesModule forDeserialization");
            BasicBeanDescription desc = super.forDeserialization(cfg, type, r);
            if (Message.class.isAssignableFrom(type.getRawClass())) {
                return protobufBeanDescription(cfg, type, r, desc);
            }
            return desc;
        }

        /**
         * Returns the BasicBeanDescription for serialization based on the specified SerializationConfig,
         * JavaType, and MixInResolver.
         *
         * @param cfg  The SerializationConfig object.
         * @param type The JavaType object.
         * @param r    The MixInResolver object.
         * @return The BasicBeanDescription object.
         */
        @Override
        public BasicBeanDescription forSerialization(SerializationConfig cfg, JavaType type, MixInResolver r) {
            log.trace("ProtobufClassAttributesModule forSerialization");
            BasicBeanDescription desc = super.forSerialization(cfg, type, r);
            if (Message.class.isAssignableFrom(type.getRawClass())) {
                return protobufBeanDescription(cfg, type, r, desc);
            }
            return desc;
        }

        /**
         * Generate a BasicBeanDescription for a protobof bean.
         *
         * @param cfg      The MapperConfig object.
         * @param type     The JavaType of the bean.
         * @param r        The MixInResolver object.
         * @param baseDesc The base BasicBeanDescription.
         * @return The generated BasicBeanDescription.
         */
        private BasicBeanDescription protobufBeanDescription(
                MapperConfig<?> cfg, JavaType type, MixInResolver r, BasicBeanDescription baseDesc) {
            log.trace("ProtobufClassAttributesModule protobufBeanDescription");
            Map<String, FieldDescriptor> types = cache.computeIfAbsent(type.getRawClass(), this::getTypeDescriptor);

            AnnotatedClass ac = AnnotatedClassResolver.resolve(cfg, type, r);

            List<BeanPropertyDefinition> props = new ArrayList<>();

            baseDesc.findProperties().forEach(property -> {
                String name = property.getName();
                if (types.containsKey(name)) {
                    if (property.hasField()
                            && property.getField().getType().isJavaLangObject()
                            && types.get(name).getType().equals(FieldDescriptor.Type.STRING)) {
                        addStringFormatAnnotation(property);
                    }
                    props.add(property.withSimpleName(name));
                }
            });

            return new BasicBeanDescription(cfg, type, ac, new ArrayList<>(props)) {
            };
        }

        /**
         * Adds a @JsonFormat annotation to the given BeanPropertyDefinition's field,
         * if the field is of type java.lang.Object and its corresponding protobuf field type is STRING.
         *
         * @param p The BeanPropertyDefinition to add the annotation to.
         */
        private void addStringFormatAnnotation(BeanPropertyDefinition p) {
            log.trace("ProtobufClassAttributesModule addStringFormatAnnotation");
            JsonFormat jsonFormatAnnotation = AnnotationHelper.class.getAnnotation(JsonFormat.class);
            p.getField().getAllAnnotations().addIfNotPresent(jsonFormatAnnotation);
        }

        /**
         * Retrieves the descriptor of a given class type.
         *
         * @param type The class type for which to retrieve the descriptor.
         * @return A map of field descriptors, with field names and corresponding FieldDescriptor objects.
         */
        private Map<String, FieldDescriptor> getTypeDescriptor(Class<?> type) {
            log.trace("ProtobufClassAttributesModule getTypeDescriptor");
            try {
                Descriptor descriptor =
                        (Descriptor) type.getMethod("getDescriptor").invoke(null);
                Map<String, FieldDescriptor> typeDescriptors = new HashMap<>();
                descriptor.getFields().forEach(fieldDescriptor -> {
                    typeDescriptors.put(fieldDescriptor.getName(), fieldDescriptor);
                    typeDescriptors.put(fieldDescriptor.getJsonName(), fieldDescriptor);
                });
                return typeDescriptors;
            } catch (Exception e) {
                log.error("Error getting proto descriptor for swagger UI.", e);
                return new HashMap<>();
            }
        }

        @JsonFormat(shape = Shape.STRING)
        static class AnnotationHelper {
        }
    }
}
