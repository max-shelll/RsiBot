package com.maxshelll.dispatcher.factory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageFactory {

    public static SendMessage createUnsupportedErrorMessage(Long chatId) {
        String text = """
                ❗ Received unsupported message.
                Supported message example:
                btc-usdt
                1h
                """;

        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();
    }

    public static SendMessage createRsiMessage(Long chatId, Double rsi, String symbol) {
        String text;
        if (rsi == null) {
            text = "RSI of %s - NF".formatted(symbol);
        } else {
            text = "RSI of %s - %.2f%%".formatted(symbol, rsi);
        }

        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();
    }
}
