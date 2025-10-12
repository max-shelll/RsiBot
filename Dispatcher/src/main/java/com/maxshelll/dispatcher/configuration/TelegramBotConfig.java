package com.maxshelll.dispatcher.configuration;

import com.maxshelll.dispatcher.property.TelegramProperty;
import com.maxshelll.dispatcher.service.DispatcherService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Configuration
public class TelegramBotConfig extends TelegramWebhookBot {

    private final TelegramProperty telegramProperty;
    private final DispatcherService dispatcherService;

    public TelegramBotConfig(TelegramProperty telegramProperty, DispatcherService dispatcherService) {
        super(telegramProperty.getToken());
        this.telegramProperty = telegramProperty;
        this.dispatcherService = dispatcherService;
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
        dispatcherService.updateDistribute(update);
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