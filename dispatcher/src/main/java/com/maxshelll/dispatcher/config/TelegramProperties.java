package com.maxshelll.dispatcher.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "telegram.bot")
public class TelegramProperties {
    private String username;
    private String token;
    private String url;
    private String webhookPath;
}
