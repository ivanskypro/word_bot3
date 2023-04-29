package com.dolan.namruev.kemal.word_bot.cache;

import com.dolan.namruev.kemal.word_bot.model.LawDocMaker;
import com.dolan.namruev.kemal.word_bot.model.botStates;

public interface BotStatesDataCache {
    void setCurrentConstructorDocState(long chatId, botStates botState);

    botStates getConstructorDocState(long chatId);

    LawDocMaker getConstructorDocData(long chatId);

    void saveConstructorDocData(long chatId, LawDocMaker lawDocMaker);

    void clearConstructorDocState(long chatId);
}