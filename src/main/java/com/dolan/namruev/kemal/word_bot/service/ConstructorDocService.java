package com.dolan.namruev.kemal.word_bot.service;

import com.dolan.namruev.kemal.word_bot.model.ConstructorDoc;

public interface ConstructorDocService {
    void saveResponse(ConstructorDoc constructorDoc);

    void reset(long chatId);
}
