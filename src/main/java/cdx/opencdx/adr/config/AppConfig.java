package cdx.opencdx.adr.config;

import cdx.opencdx.adr.service.OpenCDXANFProcessor;
import cdx.opencdx.adr.service.impl.LogicalExpressionProcessor;
import cdx.opencdx.adr.service.impl.LogicalExpressionUnitProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hubspot.jackson.datatype.protobuf.ProtobufModule;
import lombok.extern.slf4j.Slf4j;
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
     * @param logicalExpressionProcessor     The logical expression processor.
     * @param logicalExpressionUnitProcessor The logical expression unit processor.
     * @return The list of OpenCDXANFProcessor objects.
     */
    @Bean
    @Description("OpenCDXANFProcessors")
    public List<OpenCDXANFProcessor> openCDXANFProcessors(
            LogicalExpressionProcessor logicalExpressionProcessor,
            LogicalExpressionUnitProcessor logicalExpressionUnitProcessor) {
        return List.of(logicalExpressionProcessor, logicalExpressionUnitProcessor);
    }
}
