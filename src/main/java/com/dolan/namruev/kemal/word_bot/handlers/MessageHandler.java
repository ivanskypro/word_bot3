package com.dolan.namruev.kemal.word_bot.handlers;

import com.dolan.namruev.kemal.word_bot.cache.BotStatesDataCache;
import com.dolan.namruev.kemal.word_bot.constants.Constants;
import com.dolan.namruev.kemal.word_bot.model.LawDocMaker;
import com.dolan.namruev.kemal.word_bot.model.botStates;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
@Slf4j
public class MessageHandler {
    private final TelegramBot bot;
    private final BotStatesDataCache cache;
    private final BotStateHandler botStateHandler;

    @Autowired
    public MessageHandler(TelegramBot bot,
                          BotStatesDataCache cache, BotStateHandler botStateHandler) {
        this.bot = bot;
        this.cache = cache;
        this.botStateHandler = botStateHandler;
    }

    public void handle(Message message) throws IllegalStateException {
        Long chatId = message.chat().id();
        String text = message.text();
        botStates currentState = cache.getBotState(chatId);
        LawDocMaker currentDataCache = cache.getDataCache(chatId);
        if (text.equals(Constants.CANCELLED)) {
            botStateHandler.handleBotState(chatId, currentDataCache, text, botStates.STATE_CANCELLED);
            return;
        }
        if (currentState == null || currentState == botStates.STATE_START) {
            if (text.equals(Constants.START)) {
                botStateHandler.handleBotState(chatId, currentDataCache, text, botStates.STATE_START);
            } else {
                bot.execute(new SendMessage(chatId, "Бот стартует только с команды /start"));
            }
        } else if (text.equals(Constants.START)) {
            botStateHandler.handleBotState(chatId, currentDataCache, text, botStates.STATE_START);
        } else {
            botStateHandler.handleBotState(chatId, currentDataCache, text, currentState);
        }
    }
    private File getFile(Message inputMessage) {
        List<PhotoSize> photos = List.of(inputMessage.photo());
        PhotoSize photo = photos.stream()
                .max(Comparator.comparing(PhotoSize::fileSize)).orElse(null);
        GetFile request = null;
        if (photo != null) {
            request = new GetFile(photo.fileId());
        }
        GetFileResponse getFileResponse = bot.execute(request);
        return getFileResponse.file();
    }
}