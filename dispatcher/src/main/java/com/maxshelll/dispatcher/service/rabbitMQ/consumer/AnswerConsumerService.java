package com.maxshelll.dispatcher.service.rabbitMQ.consumer;

import com.maxshelll.dispatcher.TelegramBot;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnswerConsumerService{

    private final TelegramBot telegramBot;

    @SneakyThrows
    @RabbitListener(queues = "${rabbitmq.queue.answer}", ackMode = "MANUAL")
    public void consume(SendMessage sendMessage,
                        Channel channel,
                        @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        try {
            telegramBot.execute(sendMessage);
            channel.basicAck(tag, false);
        } catch (TelegramApiException e) {
            log.error("❌ Failed to send message to Telegram: {}", e.getMessage(), e);

            boolean requeue = e.getMessage() != null && e.getMessage().contains("429");
            channel.basicNack(tag, false, requeue);
        } catch (Exception e) {
            log.error("❌ Unexpected error while sending Telegram message: {}", e.getMessage(), e);
            channel.basicNack(tag, false, false);
        }
    }

}