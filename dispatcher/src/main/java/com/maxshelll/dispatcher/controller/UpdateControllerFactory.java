package com.maxshelll.dispatcher.controller;

import com.maxshelll.dispatcher.controller.update.UpdateController;
import com.maxshelll.dispatcher.dto.UpdateType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UpdateControllerFactory {

    private final List<UpdateController> controllers;
    private final Map<UpdateType, UpdateController> controllerMap = new EnumMap<>(UpdateType.class);

    @PostConstruct
    public void init() {
        for (UpdateController controller : controllers) {
            controllerMap.put(controller.getType(), controller);
        }
    }

    public UpdateController getController(Update update) {
        UpdateType type = UpdateType.fromUpdate(update);
        return controllerMap.get(type);
    }
}