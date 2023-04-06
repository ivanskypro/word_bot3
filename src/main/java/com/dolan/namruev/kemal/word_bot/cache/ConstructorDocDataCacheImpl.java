package com.dolan.namruev.kemal.word_bot.cache;

import com.dolan.namruev.kemal.word_bot.model.ConstructorDoc;
import com.dolan.namruev.kemal.word_bot.model.ConstructorDocState;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * In-memory cache.
 * constructorDocState: chat_id and Users bot state
 * ConstructorDocData(model): chat_id and Users data.
 */

@Component
public class ConstructorDocDataCacheImpl implements ConstructorDocDataCache {
    private final Map<Integer, ConstructorDocState> constructorDocState = new HashMap<>();
    private final Map<Integer, ConstructorDoc> ConstructorDocData = new HashMap<>();

    @Override
    public void setCurrentConstructorDocState(long chatId, ConstructorDocState botState) {
        constructorDocState.put((int) chatId, botState);
    }

    @Override
    public ConstructorDocState getConstructorDocState(long chatId) {
        ConstructorDocState botState = constructorDocState.get((int) chatId);
        if (botState == null) {
            botState = ConstructorDocState.STATE_START;
        }
        return botState;
    }

    @Override
    public ConstructorDoc getConstructorDocData(long chatId) {
        ConstructorDoc constructorDoc = ConstructorDocData.get((int) chatId);
        if (constructorDoc == null) {
            constructorDoc = new ConstructorDoc();
        }
        return constructorDoc;
    }

    @Override
    public void saveConstructorDocData(long chatId, ConstructorDoc constructorDoc) {
        ConstructorDocData.put((int) chatId, constructorDoc);
    }
    @Override
    public void clearConstructorDocState(long chatId) {
        constructorDocState.remove((int)chatId);
    }
}