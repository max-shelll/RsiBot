package com.maxshelll.dispatcher.controller.update;

import com.maxshelll.dispatcher.enumeration.UpdateType;
import com.maxshelll.dispatcher.property.GatewayProperty;
import com.maxshelll.dispatcher.property.rabbitmq.RabbitMQProperty;
import com.maxshelll.dispatcher.service.producer.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.net.URI;

@Controller
@RequiredArgsConstructor
public class MessageController implements UpdateController {

    private final ProducerService producerService;
    private final RabbitMQProperty rabbitMQProperty;
    private final GatewayProperty gatewayProperty;
    private final WebClient webClient;

    @Override
    public void request(Update update) {

        var message = update.getMessage().getText().toLowerCase();

        if (message.contains("usdt")) {
            requestRsiParam(update);
            return;
        }

        String queueName = rabbitMQProperty.getQueue().getMessage();
        producerService.produce(queueName, update);
    }

    @Override
    public UpdateType getType() {
        return UpdateType.MESSAGE;
    }

    private void requestRsiParam(Update update) {
        var message = update.getMessage().getText().toLowerCase();

        var symbol = message.split("\n")[0];
        var interval = message.split("\n")[1];

        URI uri = UriComponentsBuilder.fromHttpUrl(gatewayProperty.getRsi())
                .queryParam("symbol", symbol)
                .queryParam("interval", interval)
                .build()
                .toUri();

        var rsiValue = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(Integer.class)
                .block();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText("RSI of %s - %d".formatted(symbol, rsiValue));

        String queueName = rabbitMQProperty.getQueue().getAnswer();
        producerService.produceAnswer(queueName, sendMessage);
    }
}
