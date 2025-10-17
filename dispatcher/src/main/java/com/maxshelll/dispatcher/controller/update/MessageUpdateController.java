package com.maxshelll.dispatcher.controller.update;

import com.maxshelll.dispatcher.dto.UpdateType;
import com.maxshelll.dispatcher.service.rabbitMQ.producer.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Update;

@Controller
@RequiredArgsConstructor
public class MessageUpdateController implements UpdateController {

    private final ProducerService producerService;

    @Override
    public void request(Update update) {
        producerService.produceMessage(update);
    }


    @Override
    public UpdateType getType() {
        return UpdateType.MESSAGE;
    }
}
