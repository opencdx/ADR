package cdx.opencdx.adr.config;

import cdx.opencdx.adr.repository.TinkarConceptRepository;
import cdx.opencdx.adr.service.IKMInterface;
import cdx.opencdx.adr.service.OpenCDXANFProcessor;
import cdx.opencdx.adr.service.impl.IKMInterfaceImpl;
import cdx.opencdx.adr.service.impl.LogicalExpressionProcessor;
import cdx.opencdx.adr.service.impl.MapInterfaceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hubspot.jackson.datatype.protobuf.ProtobufModule;
import io.swagger.v3.core.jackson.ModelResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Primary;

import java.util.List;

/**
 * The AppConfig class is a configuration class that defines various beans used by the system.
 * It is annotated with @Configuration to indicate that it is a configuration class.
 * <p>
 * This class provides the following functionalities:
 * - Defines an ObjectMapper bean with all required registered modules.
 * - Defines a list of OpenCDXANFProcessor beans with required dependencies.
 */
@Slf4j
@Configuration
public class AppConfig {

    /**
     * IKM Interface
     *
     * @param pathParent Parent path
     * @param pathChild  Child path
     * @return IKM Interface
     */
    @Bean
    @Primary
    @Description("IKM Interface")
    @ConditionalOnProperty(prefix = "data.ikm", name = "enabled", havingValue = "true")
    public IKMInterface ikmInterface(@Value("${data.path.parent}") String pathParent,
                                     @Value("${data.path.child}") String pathChild,
                                     TinkarConceptRepository conceptRepository) {
        log.info("Creating IKM Interface");
        return new IKMInterfaceImpl(pathParent, pathChild, conceptRepository);
    }

    @Bean
    @Description("Mocked IKM Interface")
    @ConditionalOnMissingBean(IKMInterface.class)
    public IKMInterface mockedIKMInterface(TinkarConceptRepository conceptRepository) {
        log.info("Creating Mocked IKM Interface");
        return new MapInterfaceImpl(conceptRepository);
    }

    /**
     * Jackson ObjectMapper with all required registered modules.
     *
     * @return the configured ObjectMapper instance
     */
    @Bean
    @Primary
    @Description("Jackson ObjectMapper with all required registered modules.")
    public ObjectMapper objectMapper() {
        log.info("Creating ObjectMapper for use by system");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new ProtobufModule());
        mapper.registerModule(new ProtobufClassAttributesModule());
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    /**
     * Opens the CDX ANF Processors.
     *
     * @param logicalExpressionProcessor The logical expression processor.
     * @return The list of OpenCDXANFProcessor objects.
     */
    @Bean
    @Description("OpenCDXANFProcessors")
    public List<OpenCDXANFProcessor> openCDXANFProcessors(
            LogicalExpressionProcessor logicalExpressionProcessor) {
        return List.of(logicalExpressionProcessor);
    }

    /**
     * Model Resolver for Swagger
     *
     * @param objectMapper Object Mapper to use.
     * @return Model Resolver for Swagger
     */
    @Bean
    public ModelResolver modelResolver(final ObjectMapper objectMapper) {
        log.trace("Creating Model Resolver for Swagger");
        return new ModelResolver(objectMapper);
    }
}
