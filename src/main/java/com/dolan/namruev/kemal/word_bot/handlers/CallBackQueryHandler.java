package com.dolan.namruev.kemal.word_bot.handlers;

import com.dolan.namruev.kemal.word_bot.constants.Constants;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.request.ReplyKeyboardRemove;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CallBackQueryHandler {
    private final TelegramBot bot;

    @Autowired
    public CallBackQueryHandler(TelegramBot bot) {
        this.bot = bot;
    }

    public void onCallbackQuery(CallbackQuery callbackQuery) {
        String data = callbackQuery.data();
        if (data.equals(Constants.START)) {
            bot.execute(new AnswerCallbackQuery(callbackQuery.id())
                    .text("Starting over..."));
            // вызов команды /start
            bot.execute(new SendMessage(callbackQuery.message().chat().id(), "/start")
                    .replyMarkup(new ReplyKeyboardRemove()));
        } else {
            bot.execute(new AnswerCallbackQuery(callbackQuery.id())
                    .text("Unknown callback"));
        }
    }
}