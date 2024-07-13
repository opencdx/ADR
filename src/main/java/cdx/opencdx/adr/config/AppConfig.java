package cdx.opencdx.adr.config;

import cdx.opencdx.adr.service.OpenCDXANFProcessor;
import cdx.opencdx.adr.service.impl.OpenCDXSnowmedProcessorImpl;
import cdx.opencdx.adr.service.impl.OpenCDXTinkarProcessorImpl;
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
 * Application configuration.

 */
@Slf4j
@Configuration
public class AppConfig {

    /**
     * Create an ObjectMapper for use by the system.
     * @return ObjectMapper
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
     * Create a list of OpenCDXANFProcessors for use by the system.
     * @param openCDXSnowmedProcessor
     * @param openCDXTinkarProcessor
     * @return List<OpenCDXANFProcessor>
     */
    @Bean
    public List<OpenCDXANFProcessor> openCDXANFProcessors(OpenCDXSnowmedProcessorImpl openCDXSnowmedProcessor, OpenCDXTinkarProcessorImpl openCDXTinkarProcessor) {
        return List.of(openCDXSnowmedProcessor, openCDXTinkarProcessor);
    }
}
