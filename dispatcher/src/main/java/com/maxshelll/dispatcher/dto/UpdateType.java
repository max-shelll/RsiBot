package com.maxshelll.dispatcher.dto;

import org.telegram.telegrambots.meta.api.objects.Update;

public enum UpdateType {
    MESSAGE,
    COMMAND,
    VOICE,
    DOCUMENT,
    PHOTO,
    AUDIO,
    VIDEO,
    STICKER,
    ANIMATION,
    CALLBACK_QUERY,
    UNKNOWN;

    public static UpdateType fromUpdate(Update update) {
        if (update == null) return UNKNOWN;
        if (update.hasCallbackQuery()) return CALLBACK_QUERY;

        if (update.hasMessage() && update.getMessage() != null) {
            var message = update.getMessage();
            if (message.hasText() && message.getText().startsWith("/")) return COMMAND;
            if (message.hasText()) return MESSAGE;
            if (message.hasVoice()) return VOICE;
            if (message.hasDocument()) return DOCUMENT;
            if (message.hasPhoto()) return PHOTO;
            if (message.hasAudio()) return AUDIO;
            if (message.hasVideo()) return VIDEO;
            if (message.hasSticker()) return STICKER;
            if (message.hasAnimation()) return ANIMATION;
        }

        return UNKNOWN;
    }
}