package com.maxshelll.dispatcher.configuration;

import com.maxshelll.dispatcher.property.rabbitmq.RabbitMQProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {

    private final RabbitMQProperty rabbitMQProperty;

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue messageQueue() {
        return QueueBuilder.durable(rabbitMQProperty.getQueue().getMessage()).build();
    }

    @Bean
    public Queue answerQueue() {
        return QueueBuilder.durable(rabbitMQProperty.getQueue().getAnswer()).build();
    }
}