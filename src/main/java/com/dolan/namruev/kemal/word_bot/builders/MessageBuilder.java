package com.dolan.namruev.kemal.word_bot.builders;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
}