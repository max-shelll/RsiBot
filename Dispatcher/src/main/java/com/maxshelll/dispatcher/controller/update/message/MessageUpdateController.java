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
        String message = update.getMessage().getText().toLowerCase();
        String[] lines = message.split("\n");

        if ((message.contains("-usdt") || message.contains("-usdc")) && lines.length == 2) {
            String symbol = lines[0];
            String interval = lines[1];
            Long chatId = update.getMessage().getChatId();

            messageUpdateService.requestCoin(chatId, symbol, interval);
        } else {
            messageUpdateService.requestUnsupportedText(update);
        }
    }


    @Override
    public UpdateType getType() {
        return UpdateType.MESSAGE;
    }
}
