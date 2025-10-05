package com.maxshelll.dispatcher.service.consumer;

import com.maxshelll.dispatcher.configuration.TelegramBotConfig;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
@RequiredArgsConstructor
public class AnswerConsumerServiceImpl implements AnswerConsumerService {

    private final TelegramBotConfig telegramBot;

    @Override
    @SneakyThrows
    @RabbitListener(queues = "answer_queue")
    public void consume(SendMessage sendMessage) {
        telegramBot.execute(sendMessage);
    }
}