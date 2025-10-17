package com.maxshelll.dispatcher.service.rabbitMQ.consumer;

import com.maxshelll.dispatcher.factory.MessageFactory;
import com.maxshelll.dispatcher.service.rabbitMQ.BingXRSIService;
import com.maxshelll.dispatcher.service.rabbitMQ.producer.ProducerService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class MessageConsumerService {

    private final ProducerService producerService;
    private final BingXRSIService bingXRSIService;

    @SneakyThrows
    @RabbitListener(queues = "${rabbitmq.queue.message}")
    public void consume(Update update) {
        String message = update.getMessage().getText().toLowerCase();
        String[] lines = message.split("\n");

        if ((message.contains("-usdt") || message.contains("-usdc")) && lines.length == 2) {
            String symbol = lines[0];
            String interval = lines[1];
            Long chatId = update.getMessage().getChatId();
            Double rsi = bingXRSIService.request(symbol, interval);

            producerService.produceAnswer(MessageFactory.createRsiMessage(chatId, rsi, symbol));
        } else {
            producerService.produceAnswer(MessageFactory.createUnsupportedErrorMessage(update.getMessage().getChatId()));
        }
    }
}
