package com.dolan.namruev.kemal.word_bot.cache;

import com.dolan.namruev.kemal.word_bot.model.LawDocMaker;
import com.dolan.namruev.kemal.word_bot.model.botStates;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * In-memory cache.
 * constructorDocState: chat_id and Users bot state
 * ConstructorDocData(model): chat_id and Users data.
 */

@Component
public class BotStatesDataCacheImpl implements BotStatesDataCache {
    private final Map<Integer, botStates> constructorDocState = new HashMap<>();
    private final Map<Integer, LawDocMaker> ConstructorDocData = new HashMap<>();

    @Override
    public void setCurrentConstructorDocState(long chatId, botStates botState) {
        constructorDocState.put((int) chatId, botState);
    }

    @Override
    public botStates getConstructorDocState(long chatId) {
        botStates botState = constructorDocState.get((int) chatId);
        if (botState == null) {
            botState = botStates.STATE_START;
        }
        return botState;
    }

    @Override
    public LawDocMaker getConstructorDocData(long chatId) {
        LawDocMaker lawDocMaker = ConstructorDocData.get((int) chatId);
        if (lawDocMaker == null) {
            lawDocMaker = new LawDocMaker();
        }
        return lawDocMaker;
    }

    @Override
    public void saveConstructorDocData(long chatId, LawDocMaker lawDocMaker) {
        ConstructorDocData.put((int) chatId, lawDocMaker);
    }

    @Override
    public void clearConstructorDocState(long chatId) {
        constructorDocState.remove((int) chatId);
    }
}