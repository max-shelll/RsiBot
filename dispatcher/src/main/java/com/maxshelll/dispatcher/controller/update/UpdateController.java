package com.maxshelll.dispatcher.controller.update;

import com.maxshelll.dispatcher.enumeration.UpdateType;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateController {
    void request(Update update);
    UpdateType getType();
}
