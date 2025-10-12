package com.maxshelll.dispatcher.controller.update.message.service;

import com.maxshelll.dispatcher.property.GatewayProperty;
import com.maxshelll.dispatcher.service.rabbitMQ.producer.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class MessageUpdateService {

    private final ProducerService producerService;
    private final GatewayProperty gatewayProperty;
    private final WebClient webClient;

    public void requestUnsupportedText(Update update) {
        String text = """
                ❗ Received unsupported message.
                Supported message example:
                btc-usdt
                1h
                """;

        SendMessage sendMessage = SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(text)
                .build();

        producerService.produceAnswer(sendMessage);
    }

    public void requestCoin(Long chatId, String symbol, String interval) {
        URI uri = UriComponentsBuilder.fromHttpUrl(gatewayProperty.getRsi())
                .queryParam("symbol", symbol.toUpperCase())
                .queryParam("interval", interval)
                .build()
                .toUri();

        Double rsiValue = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(Double.class)
                .block();

        String text;
        if (rsiValue == null) {
            text = "RSI of %s - NF".formatted(symbol);
        } else {
            text = "RSI of %s - %.2f%%".formatted(symbol, rsiValue);
        }

        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();

        producerService.produceAnswer(sendMessage);
    }
}
