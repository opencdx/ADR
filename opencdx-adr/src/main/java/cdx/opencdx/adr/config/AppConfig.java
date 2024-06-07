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

import cdx.opencdx.adr.service.OpenCDXMessageService;
import cdx.opencdx.adr.service.impl.NatsOpenCDXMessageServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.tracing.Tracer;
import io.nats.client.Connection;
import io.nats.client.ConnectionListener;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Primary;

/**
 * Applicaiton Configuration
 */
@Slf4j
@AutoConfiguration
@Configuration
@EnableConfigurationProperties(AppProperties.class)
public class AppConfig {
    /**
     * Default Constructor
     */
    public AppConfig() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }

    
    @Bean
    @Generated
    public ConnectionListener createConnectionListener() {
        return (conn, type) -> log.error(
                "Connection Event: {}  Connection: {}", type, conn.getStatus().name());
    }

    @Bean
    @Description("NATS implementation of the OpenCDXMessageService.")
    public OpenCDXMessageService natsOpenCDXMessageService(
            Connection natsConnection,
            ObjectMapper objectMapper,
            @Value("${spring.application.name}") String applicationName,
            Tracer tracer) {
        log.trace("Using NATS based Messaging Service");
        return new NatsOpenCDXMessageServiceImpl(
                natsConnection, objectMapper, applicationName, tracer);
    }

    /**
     * Bean indicating the format of the message string
     * @param appProperties properties that contain the message format.
     * @return String providing the format.
     */
    @Bean
    @Description("Demonstration on how to document a bean.")
    public String format(AppProperties appProperties) {
        return appProperties.getFormat();
    }
}
