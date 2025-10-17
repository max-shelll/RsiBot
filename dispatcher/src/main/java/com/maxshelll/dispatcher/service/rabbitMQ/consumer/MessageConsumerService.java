package com.maxshelll.dispatcher.service.rabbitMQ.consumer;

import com.maxshelll.dispatcher.config.GatewayProperties;
import com.maxshelll.dispatcher.service.rabbitMQ.producer.ProducerService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class MessageConsumerService {

    private final ProducerService producerService;
    private final GatewayProperties gatewayProperty;
    private final WebClient webClient;

    @SneakyThrows
    @RabbitListener(queues = "${rabbitmq.queue.message}")
    public void consume(Update update) {
        String message = update.getMessage().getText().toLowerCase();
        String[] lines = message.split("\n");

        if ((message.contains("-usdt") || message.contains("-usdc")) && lines.length == 2) {
            String symbol = lines[0];
            String interval = lines[1];
            Long chatId = update.getMessage().getChatId();

            requestCoin(chatId, symbol, interval);
        } else {
            requestUnsupportedText(update);
        }
    }

    private void requestUnsupportedText(Update update) {
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

    private void requestCoin(Long chatId, String symbol, String interval) {
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
