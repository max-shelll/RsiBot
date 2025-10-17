package com.maxshelll.dispatcher.controller.update;

import com.maxshelll.dispatcher.service.rabbitMQ.producer.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Update;

@Controller
@RequiredArgsConstructor
public class MessageUpdateController {

    private final ProducerService producerService;

    public void request(Update update) {
        if (update.hasMessage() && update.getMessage().hasText())
            producerService.produceMessage(update);
    }

}
