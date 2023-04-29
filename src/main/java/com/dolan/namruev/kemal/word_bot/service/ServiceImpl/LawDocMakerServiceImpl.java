package com.dolan.namruev.kemal.word_bot.service.ServiceImpl;

import com.dolan.namruev.kemal.word_bot.model.LawDocMaker;
import com.dolan.namruev.kemal.word_bot.repository.LawDocMakerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LawDocMakerServiceImpl implements com.dolan.namruev.kemal.word_bot.service.LawDocMakerService {

    private static final Logger logger = LoggerFactory.getLogger(LawDocMakerServiceImpl.class);
    private final LawDocMakerRepository lawDocMakerRepository;

    public LawDocMakerServiceImpl(LawDocMakerRepository lawDocMakerRepository) {
        this.lawDocMakerRepository = lawDocMakerRepository;
    }

    @Override
    public void saveResponse(LawDocMaker lawDocMaker) {
        LawDocMaker saveResponse = lawDocMakerRepository.save(lawDocMaker);
        logger.info("Response successfully saved " + saveResponse);
    }

    @Override
    public void reset(long chatId) {
        lawDocMakerRepository.deleteById(chatId);
        logger.info("Response successfully deleted");
    }
}