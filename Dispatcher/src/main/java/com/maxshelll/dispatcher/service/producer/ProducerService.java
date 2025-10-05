package com.maxshelll.dispatcher.service.producer;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface ProducerService {
    void produce(String rabbitQueue, Update update);
    void produceAnswer(String rabbitQueue, SendMessage sendMessage);
}
