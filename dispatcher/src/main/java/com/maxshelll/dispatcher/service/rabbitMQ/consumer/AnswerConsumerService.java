package com.maxshelll.dispatcher.service.rabbitMQ.consumer;

import com.maxshelll.dispatcher.TelegramBot;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
@RequiredArgsConstructor
public class AnswerConsumerService{

    private final TelegramBot telegramBot;

    @SneakyThrows
    @RabbitListener(queues = "${rabbitmq.queue.answer}")
    public void consume(SendMessage sendMessage) {
        telegramBot.execute(sendMessage);
    }
}