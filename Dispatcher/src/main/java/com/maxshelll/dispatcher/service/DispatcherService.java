package com.maxshelll.dispatcher.service;

import com.maxshelll.dispatcher.controller.UpdateControllerFactory;
import com.maxshelll.dispatcher.controller.update.UpdateController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Service
@RequiredArgsConstructor
public class DispatcherService {

    private final UpdateControllerFactory controllerFactory;

    public void updateDistribute(Update update) {
        UpdateController controller = controllerFactory.getController(update);
        if (controller != null) {
            controller.request(update);
            return;
        }

        log.warn("Unsupported update type: {}", update);
    }
}