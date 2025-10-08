package com.maxshelll.dispatcher.controller.update.message.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface MessageUpdateService {
    void request(Update update);
    void request(Long chatId, String symbol, String interval);
}
