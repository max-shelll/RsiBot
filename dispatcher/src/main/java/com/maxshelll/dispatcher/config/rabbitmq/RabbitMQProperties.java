package com.maxshelll.dispatcher.config.rabbitmq;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "rabbitmq")
public class RabbitMQProperties {
    private String host;
    private int port;
    private String username;
    private String password;
    private RabbitMQQueueProperties queue;
}
