package com.maxshelll.dispatcher.service.rabbitMQ.producer;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface ProducerService {
    void produceMessage(Update update);
    void produceAnswer(SendMessage sendMessage);
}
