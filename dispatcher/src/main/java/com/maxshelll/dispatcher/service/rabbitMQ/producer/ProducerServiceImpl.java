package com.maxshelll.dispatcher.service.rabbitMQ.producer;

import com.maxshelll.dispatcher.config.rabbitmq.RabbitMQProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class ProducerServiceImpl implements ProducerService {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQProperties rabbitMQProperty;

    @Override
    public void produceMessage(Update update) {
        rabbitTemplate.convertAndSend(rabbitMQProperty.getQueue().getMessage(), update);
    }

    @Override
    public void produceAnswer(SendMessage sendMessage) {
        rabbitTemplate.convertAndSend(rabbitMQProperty.getQueue().getAnswer(), sendMessage);
    }
}
