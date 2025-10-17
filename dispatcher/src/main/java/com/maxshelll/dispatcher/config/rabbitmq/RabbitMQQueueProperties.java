package com.maxshelll.dispatcher.config.rabbitmq;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "rabbitmq.queue")
public class RabbitMQQueueProperties {
    private String message;
    private String answer;
}