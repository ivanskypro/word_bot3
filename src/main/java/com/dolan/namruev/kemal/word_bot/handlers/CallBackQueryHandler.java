package com.dolan.namruev.kemal.word_bot.handlers;

import com.dolan.namruev.kemal.word_bot.cache.BotStatesDataCache;
import com.dolan.namruev.kemal.word_bot.constants.Constants;
import com.dolan.namruev.kemal.word_bot.model.LawDocMaker;
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
    private final BotStatesDataCache dataCache;

    @Autowired
    public CallBackQueryHandler(TelegramBot bot, BotStateHandler botStateHandler, BotStatesDataCache dataCache) {
        this.bot = bot;
        this.botStateHandler = botStateHandler;
        this.dataCache = dataCache;
    }

    public void onCallbackQuery(CallbackQuery callbackQuery) {
        String data = callbackQuery.data();
        Long chatId = callbackQuery.message().chat().id();
        LawDocMaker docMaker = dataCache.getDataCache(chatId);
        if (data.equals(Constants.CANCELLED_TEXT)) {
            bot.execute(new AnswerCallbackQuery(callbackQuery.id())
                    .text("Давай начнем сначала"));
            botStateHandler.handleCancelledState(chatId);
        }
        if (data.equals(Constants.GET_DOCUMENT)) {
            bot.execute(new AnswerCallbackQuery(callbackQuery.id())
                    .text("Ваш документ отправлен"));
            botStateHandler.handleCompleteState(chatId, docMaker);
        }
        else {
            bot.execute(new AnswerCallbackQuery(callbackQuery.id())
                    .text("Тру-ту-ту"));
        }
    }
}