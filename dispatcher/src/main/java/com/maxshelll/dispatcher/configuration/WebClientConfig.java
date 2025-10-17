package com.maxshelll.dispatcher.configuration;

import com.maxshelll.dispatcher.property.GatewayProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private final GatewayProperty gatewayProperty;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(gatewayProperty.getBase())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

}
