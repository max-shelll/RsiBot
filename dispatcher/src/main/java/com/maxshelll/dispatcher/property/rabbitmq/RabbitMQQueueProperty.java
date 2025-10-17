package com.maxshelll.dispatcher.property.rabbitmq;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "rabbitmq.queue")
public class RabbitMQQueueProperty {
    private String message;
    private String answer;
}