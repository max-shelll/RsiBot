package com.maxshelll.dispatcher.controller;

import com.maxshelll.dispatcher.controller.update.MessageUpdateController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TelegramWebhookController {

    private final MessageUpdateController  messageUpdateController;

    @PostMapping("/callback/update")
    public ResponseEntity<Void> request(@RequestBody Update update) {
        if (update == null) {
            log.error("Received update is null");
            return ResponseEntity.ok().build();
        }

        messageUpdateController.request(update);
        return ResponseEntity.ok().build();
    }
}