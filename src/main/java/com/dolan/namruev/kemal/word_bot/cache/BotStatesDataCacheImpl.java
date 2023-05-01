package com.dolan.namruev.kemal.word_bot.cache;

import com.dolan.namruev.kemal.word_bot.model.LawDocMaker;
import com.dolan.namruev.kemal.word_bot.model.botStates;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * In-memory cache.
 * statesMap: chat_id and Users bot state
 * dataCacheMap(model): chat_id and Users data.
 */

@Component
public class BotStatesDataCacheImpl implements BotStatesDataCache {
    private final Map<Long, botStates> statesMap = new HashMap<>();
    private final Map<Long, LawDocMaker> dataCacheMap = new HashMap<>();

    @Override
    public void setCurrentBotState(Long chatId, botStates botState) {
        statesMap.put(chatId, botState);
    }

    @Override
    public botStates getBotState(Long chatId) {
        botStates botState = statesMap.get(chatId);
        if (botState == null) {
            botState = botStates.STATE_START;
        }
        return botState;
    }

    @Override
    public LawDocMaker getDataCache(Long chatId) {
        LawDocMaker lawDocMaker = dataCacheMap.get(chatId);
        if (lawDocMaker == null) {
            lawDocMaker = new LawDocMaker();
            dataCacheMap.put(chatId, lawDocMaker);
        }
        return lawDocMaker;
    }

    @Override
    public void saveDataCache(Long chatId, LawDocMaker lawDocMaker) {
        dataCacheMap.put(chatId, lawDocMaker);
    }

    @Override
    public void clearBotState(Long chatId) {
        statesMap.remove(chatId);
    }
}