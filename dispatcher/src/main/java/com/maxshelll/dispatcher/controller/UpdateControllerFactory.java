package com.maxshelll.dispatcher.controller;

import com.maxshelll.dispatcher.controller.update.UpdateController;
import com.maxshelll.dispatcher.dto.UpdateType;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class UpdateControllerFactory {

    private final Map<UpdateType, UpdateController> controllers;

    public UpdateControllerFactory(List<UpdateController> controllerList) {
        this.controllers = createControllerMap(controllerList);
    }

    private Map<UpdateType, UpdateController> createControllerMap(List<UpdateController> controllerList) {
        Map<UpdateType, UpdateController> controllerMap = new EnumMap<>(UpdateType.class);
        for (UpdateController controller : controllerList) {
            controllerMap.put(controller.getType(), controller);
        }

        return controllerMap;
    }

    public UpdateController getController(Update update) {
        return controllers.get(UpdateType.fromUpdate(update));
    }
}