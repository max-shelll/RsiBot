package com.maxshelll.dispatcher.service.rabbitMQ.consumer;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface AnswerConsumerService {
    void consume(SendMessage sendMessage);
}
