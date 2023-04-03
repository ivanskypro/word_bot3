package com.dolan.namruev.kemal.word_bot.listener;

import com.dolan.namruev.kemal.word_bot.model.ConstructorDoc;
import com.dolan.namruev.kemal.word_bot.repository.ConstructorDocRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final TelegramBot telegramBot;
    private final ConstructorDocRepository constructorDocRepository;

    public TelegramBotUpdatesListener(TelegramBot telegramBot,
                                      ConstructorDocRepository constructorDocRepository) {
        this.telegramBot = telegramBot;
        this.constructorDocRepository = constructorDocRepository;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }
    private final Map<Long, CompletableFuture<String>> chatIdToFuture =
            new ConcurrentHashMap<>();

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
                Long chatId = update.message().chat().id();
                String messageText = update.message().text();
                if (update.message().text() != null) {
                    logger.info("Словил сообщение");
                    if (messageText.equals("/start")) {
                        Keyboard keyboard = new ReplyKeyboardMarkup("заполнить ходатайство");
                        SendMessage request = new SendMessage(chatId, "Привет! " +
                                "Это бот для быстрого заполнения ходатайства " +
                                "об отложении судебного заседния")
                                .replyMarkup(keyboard);
                        telegramBot.execute(request);
                    }
                    if (messageText.equals("заполнить ходатайство")) {
                        SendMessage message = new SendMessage(chatId, "Напиши название суда");
                        telegramBot.execute(message);
                        chatIdToFuture.put(chatId, new CompletableFuture<>());
                    } else {
                        CompletableFuture<String> future = chatIdToFuture.get(chatId);
                        if (future != null && !future.isDone()) {
                            future.complete(messageText); // сохраняем ответ пользователя в Future
                            ConstructorDoc constructorDoc = new ConstructorDoc();
                            constructorDoc.setTextCourtName(messageText);
                            constructorDocRepository.save(constructorDoc);
                            SendMessage request = new SendMessage(chatId, "Суд "
                                    + messageText
                                    + " сохранен," +
                                    "а теперь напиши адрес");
                            telegramBot.execute(request);

                        } else {
                            CompletableFuture<String> future1 = chatIdToFuture.get(chatId);
                            if (future1 != null && future1.isDone()) {
                                String courtName = future1.getNow(null);
                                ConstructorDoc constructorDoc = constructorDocRepository
                                        .findByTextCourtName(courtName)
                                        .orElseThrow(() ->
                                                new NoSuchElementException("courtName - not found"));

                                constructorDoc.setTextCourtAddress(messageText);
                                constructorDocRepository.save(constructorDoc);
                                SendMessage request = new SendMessage(chatId, "адрес суда "
                                        + messageText + " сохранено");
                                telegramBot.execute(request);
                            }
                        }
                    }
                }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}