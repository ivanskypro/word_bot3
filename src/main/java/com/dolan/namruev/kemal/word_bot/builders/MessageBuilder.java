package com.dolan.namruev.kemal.word_bot.builders;

import com.dolan.namruev.kemal.word_bot.constants.Constants;
import com.dolan.namruev.kemal.word_bot.model.LawDocMaker;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Slf4j
public class MessageBuilder {
    private final TelegramBot bot;
    public MessageBuilder(TelegramBot bot) {
        this.bot = bot;
    }

    public void sendMessage(Long chatId,
                            String inputMessage,
                            Keyboard keyboard) {
        SendMessage outputMessage = new SendMessage(chatId, inputMessage)
                .replyMarkup(keyboard);
        try {
            bot.execute(outputMessage);
        } catch (Exception e) {
            log.info("Exception was thrown in sendMessage method with keyboard ");
            e.printStackTrace();
        }
    }
    public void sendDocument(long chatId, LawDocMaker lawDocMaker) {
        String fileName = lawDocMaker.getOption() + ".docx";
        File file = new File(Constants.OUTPUT_PATH+fileName);
        if (!file.exists()) {
            throw new IllegalStateException("Document file does not exist");
        }
        SendDocument request = new SendDocument(chatId, file);
        bot.execute(request);
        boolean deleted = file.delete();
        if (deleted){
            log.info("temporary file deleted: " + file);
        } else {
            log.info("temporary file not deleted: " + file);
        }
    }
}