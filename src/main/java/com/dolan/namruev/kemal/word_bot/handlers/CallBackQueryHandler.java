package com.dolan.namruev.kemal.word_bot.handlers;

import com.dolan.namruev.kemal.word_bot.constants.Constants;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CallBackQueryHandler {
    private final TelegramBot bot;
    private final BotStateHandler botStateHandler;

    @Autowired
    public CallBackQueryHandler(TelegramBot bot, BotStateHandler botStateHandler) {
        this.bot = bot;
        this.botStateHandler = botStateHandler;
    }

    public void onCallbackQuery(CallbackQuery callbackQuery) {
        String data = callbackQuery.data();
        if (data.equals(Constants.CANCELLED)) {
            bot.execute(new AnswerCallbackQuery(callbackQuery.id())
                    .text("Давай начнем сначала"));
            botStateHandler.handleCancelledState(callbackQuery.message().chat().id());
        }
        if (data.equals(Constants.START)) {
            bot.execute(new AnswerCallbackQuery(callbackQuery.id())
                    .text("Давай начнем сначала"));
            botStateHandler.handleStartState(callbackQuery.message().chat().id());
        }
        else {
            bot.execute(new AnswerCallbackQuery(callbackQuery.id())
                    .text("Тру-ту-ту"));
        }
    }
}