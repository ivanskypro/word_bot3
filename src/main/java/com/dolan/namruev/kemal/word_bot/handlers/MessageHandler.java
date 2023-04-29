package com.dolan.namruev.kemal.word_bot.handlers;

import com.dolan.namruev.kemal.word_bot.cache.BotStatesDataCache;
import com.dolan.namruev.kemal.word_bot.constants.Constants;
import com.dolan.namruev.kemal.word_bot.model.LawDocMaker;
import com.dolan.namruev.kemal.word_bot.model.botStates;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageHandler {
    private final TelegramBot bot;
    private final BotStatesDataCache cacheState;
    private final LawDocMaker lawDocMaker = new LawDocMaker();
    private final BotStateHandler botStateHandler;

    @Autowired
    public MessageHandler(TelegramBot bot,
                          BotStatesDataCache cacheState, BotStateHandler botStateHandler) {
        this.bot = bot;
        this.cacheState = cacheState;
        this.botStateHandler = botStateHandler;
    }

    public void handle(Message message) throws IllegalStateException {
        Long chatId = message.chat().id();
        String text = message.text();
        botStates currentState = cacheState.getConstructorDocState(chatId);
        if (text.equals(Constants.CANCELLED)) {
            botStateHandler.handleBotState(chatId, lawDocMaker, text, botStates.STATE_CANCELLED);
            return;
        }
        if (currentState == null || currentState == botStates.STATE_START) {
            if (text.equals(Constants.START)) {
                botStateHandler.handleBotState(chatId, lawDocMaker, text, botStates.STATE_START);
            } else {
                bot.execute(new SendMessage(chatId, "Бот стартует только с команды /start"));
            }
        } else if (text.equals(Constants.START)) {
            botStateHandler.handleBotState(chatId, lawDocMaker, text, botStates.STATE_START);
        } else {
            botStateHandler.handleBotState(chatId, lawDocMaker, text, currentState);
        }
    }
}