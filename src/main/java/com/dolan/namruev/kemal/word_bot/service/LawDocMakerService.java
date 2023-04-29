package com.dolan.namruev.kemal.word_bot.service;

import com.dolan.namruev.kemal.word_bot.model.LawDocMaker;

public interface LawDocMakerService {
    void saveResponse(LawDocMaker lawDocMaker);

    void reset(long chatId);
}