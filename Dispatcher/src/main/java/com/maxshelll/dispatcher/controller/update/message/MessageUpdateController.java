package com.maxshelll.dispatcher.controller.update.message;

import com.maxshelll.dispatcher.controller.update.UpdateController;
import com.maxshelll.dispatcher.controller.update.message.service.MessageUpdateService;
import com.maxshelll.dispatcher.enumeration.UpdateType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Update;

@Controller
@RequiredArgsConstructor
public class MessageUpdateController implements UpdateController {

    private final MessageUpdateService messageUpdateService;

    @Override
    public void request(Update update) {
        var message = update.getMessage().getText().toLowerCase();

        if (message.contains("usdt")) {
            var symbol = message.split("\n")[0];
            var interval = message.split("\n")[1];
            var chatId = update.getMessage().getChatId();

            messageUpdateService.request(chatId, symbol, interval);
        } else {
            messageUpdateService.request(update);
        }
    }

    @Override
    public UpdateType getType() {
        return UpdateType.MESSAGE;
    }
}
