package com.maxshelll.rsiservice.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "gateway")
public class GatewayProperty {
    private String base;
    private String rsi;
}