package com.dolan.namruev.kemal.word_bot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            String helloText = "Ням-ням!";
            Long chatId = update.message().chat().id();
            String messageText = update.message().text();
            if (update.message().text() != null) {
                if (messageText.equals("/start")) {
                    SendMessage message = new SendMessage(chatId, helloText);
                    telegramBot.execute(message);
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
