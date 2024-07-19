package cdx.opencdx.adr.config;

import cdx.opencdx.adr.service.OpenCDXANFProcessor;
import cdx.opencdx.adr.service.impl.ANFLogProcessor;
import cdx.opencdx.grpc.data.ANFStatement;
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

    @Bean
    @Description("OpenCDXANFProcessors")
    public List<OpenCDXANFProcessor> openCDXANFProcessors(ANFLogProcessor anfLogProcessor) {
        return List.of(anfLogProcessor);
    }
}
