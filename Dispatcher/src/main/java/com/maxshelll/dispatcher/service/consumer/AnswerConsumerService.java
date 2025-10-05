package com.maxshelll.dispatcher.service.consumer;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface AnswerConsumerService {
    void consume(SendMessage sendMessage);
}
