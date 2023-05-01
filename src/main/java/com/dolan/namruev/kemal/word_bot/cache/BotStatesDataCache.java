package com.dolan.namruev.kemal.word_bot.cache;

import com.dolan.namruev.kemal.word_bot.model.LawDocMaker;
import com.dolan.namruev.kemal.word_bot.model.botStates;

public interface BotStatesDataCache {
    void setCurrentBotState(Long chatId, botStates botState);

    botStates getBotState(Long chatId);

    LawDocMaker getDataCache(Long chatId);

    void saveDataCache(Long chatId, LawDocMaker lawDocMaker);

    void clearBotState(Long chatId);
}