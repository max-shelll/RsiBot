package com.maxshelll.dispatcher;

import com.maxshelll.dispatcher.config.TelegramProperties;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Configuration
public class TelegramBot extends TelegramWebhookBot {

    private final TelegramProperties telegramProperty;

    public TelegramBot(TelegramProperties telegramProperty) {
        super(telegramProperty.getToken());
        this.telegramProperty = telegramProperty;
    }

    @Override
    public String getBotUsername() {
        return telegramProperty.getUsername();
    }

    @Override
    public String getBotPath() {
        return telegramProperty.getWebhookPath();
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return null;
    }

    @PostConstruct
    private void initializeWebhook() {
        try {
            SetWebhook webhook = SetWebhook.builder()
                    .url(telegramProperty.getUrl())
                    .build();

            setWebhook(webhook);
            log.info("Telegram webhook set successfully: {}", telegramProperty.getUrl());
        } catch (Exception e) {
            log.error("Failed to set Telegram webhook", e);
        }
    }
}