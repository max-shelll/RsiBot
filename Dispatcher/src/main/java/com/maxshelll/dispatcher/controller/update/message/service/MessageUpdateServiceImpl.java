package com.maxshelll.dispatcher.controller.update.message.service;

import com.maxshelll.dispatcher.property.GatewayProperty;
import com.maxshelll.dispatcher.property.rabbitmq.RabbitMQProperty;
import com.maxshelll.dispatcher.service.rabbitMQ.producer.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class MessageUpdateServiceImpl implements MessageUpdateService {

    private final RabbitMQProperty rabbitMQProperty;
    private final ProducerService producerService;
    private final GatewayProperty gatewayProperty;
    private final WebClient webClient;

    @Override
    public void request(Update update) {
        var queueName = rabbitMQProperty.getQueue().getMessage();
        producerService.produce(queueName, update);
    }

    @Override
    public void request(Long chatId, String symbol, String interval) {
        var uri = UriComponentsBuilder.fromHttpUrl(gatewayProperty.getRsi())
                .queryParam("symbol", symbol)
                .queryParam("interval", interval)
                .build()
                .toUri();

        var rsiValue = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(Integer.class)
                .block();

        var sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text("RSI of %s - %d".formatted(symbol, rsiValue))
                .build();

        producerService.produceAnswer(sendMessage);
    }
}
