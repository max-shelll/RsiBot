package com.maxshelll.dispatcher.service.rabbitMQ;

import com.maxshelll.dispatcher.config.GatewayProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class BingXRSIService {

    private final GatewayProperties gatewayProperties;
    private final WebClient webClient;

    public Double request(String symbol, String interval) {
        URI uri = UriComponentsBuilder.fromHttpUrl(gatewayProperties.getRsi())
                .queryParam("symbol", symbol.toUpperCase())
                .queryParam("interval", interval)
                .build()
                .toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(Double.class)
                .block();
    }
}
