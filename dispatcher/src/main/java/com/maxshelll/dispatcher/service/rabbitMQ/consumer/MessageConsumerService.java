package com.maxshelll.dispatcher.service.rabbitMQ.consumer;

import com.maxshelll.dispatcher.factory.MessageFactory;
import com.maxshelll.dispatcher.service.rabbitMQ.BingXRSIService;
import com.maxshelll.dispatcher.service.rabbitMQ.producer.ProducerService;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageConsumerService {

    private final ProducerService producerService;
    private final BingXRSIService bingXRSIService;

    @SneakyThrows
    @RabbitListener(queues = "${rabbitmq.queue.message}", ackMode = "MANUAL")
    public void consume(Update update,
                        Channel channel,
                        @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        try {
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
        } catch (Exception e) {
            log.error("❌ Failed to process update: {}", e.getMessage(), e);
            channel.basicNack(tag, false, false);
        }
    }
}
