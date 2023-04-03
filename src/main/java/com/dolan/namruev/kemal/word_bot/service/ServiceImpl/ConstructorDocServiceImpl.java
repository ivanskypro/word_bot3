package com.dolan.namruev.kemal.word_bot.service.ServiceImpl;

import com.dolan.namruev.kemal.word_bot.model.ConstructorDoc;
import com.dolan.namruev.kemal.word_bot.repository.ConstructorDocRepository;
import com.dolan.namruev.kemal.word_bot.service.ConstructorDocService;
import com.pengrad.telegrambot.TelegramBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ConstructorDocServiceImpl implements ConstructorDocService {

    private static final Logger logger = LoggerFactory.getLogger(ConstructorDocServiceImpl.class);    private final ConstructorDocRepository constructorDocRepository;

    public ConstructorDocServiceImpl(ConstructorDocRepository constructorDocRepository, TelegramBot telegramBot) {
        this.constructorDocRepository = constructorDocRepository;
    }

    @Override
    public ConstructorDoc saveResponse(ConstructorDoc constructorDoc){
        ConstructorDoc saveResponse = constructorDocRepository.save(constructorDoc);
        logger.info("Response successfully saved " + saveResponse);
        return saveResponse;
    }
}