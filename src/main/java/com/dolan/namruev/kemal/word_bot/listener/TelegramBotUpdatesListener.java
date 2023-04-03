package com.dolan.namruev.kemal.word_bot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class TelegramBotUpdatesListener implements UpdatesListener {
    private static final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final TelegramBot bot;
    private final MessageHandler messageHandler;

    @Autowired
    public TelegramBotUpdatesListener(TelegramBot bot, MessageHandler messageHandler) {
        this.bot = bot;
        this.messageHandler = messageHandler;
    }
    @PostConstruct
    public void init() {
        bot.setUpdatesListener(this);
    }

    /**
     * Check and process chat's updates
     *
     * @param updates used for receiving and checking different types of updates
     * @return confirmed all updates
     */

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            Message message = update.message();
            // check if the update has a message and message has text
            if (message != null) {
                messageHandler.handle(message);
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }


    private long extractChatId(Message message) {
        return message.chat().id();
    }
}