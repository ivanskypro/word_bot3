package com.dolan.namruev.kemal.word_bot.cache;

import com.dolan.namruev.kemal.word_bot.model.ConstructorDoc;
import com.dolan.namruev.kemal.word_bot.model.ConstructorDocState;

public interface ConstructorDocDataCache {
    void setCurrentConstructorDocState(long chatId, ConstructorDocState botState);

    ConstructorDocState getConstructorDocState(long chatId);

    ConstructorDoc getConstructorDocData(long chatId);
    void saveConstructorDocData(long chatId, ConstructorDoc constructorDoc);
    void clearConstructorDocState(long chatId);
}