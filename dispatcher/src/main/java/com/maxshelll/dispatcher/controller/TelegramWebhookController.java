package com.maxshelll.dispatcher.controller;

import com.maxshelll.dispatcher.service.DispatcherService;
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

    private final DispatcherService dispatcherService;

    @PostMapping("/callback/update")
    public ResponseEntity<Void> request(@RequestBody Update update) {
        if (update == null) {
            log.error("Received update is null");
            return ResponseEntity.ok().build();
        }

        dispatcherService.updateDistribute(update);
        return ResponseEntity.ok().build();
    }
}