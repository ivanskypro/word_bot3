package com.dolan.namruev.kemal.word_bot.listener;
import com.dolan.namruev.kemal.word_bot.model.ConstructorDoc;
import com.dolan.namruev.kemal.word_bot.model.ConstructorDocState;
import com.dolan.namruev.kemal.word_bot.repository.ConstructorDocRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class MessageHandler {
    private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);
    private final ConstructorDocRepository constructorDocRepository;
    private final TelegramBot bot;
    @Autowired
    public MessageHandler(ConstructorDocRepository constructorDocRepository, TelegramBot bot) {
        this.constructorDocRepository = constructorDocRepository;
        this.bot = bot;
    }

    public void handle(Message message) {
        Long chatId = message.chat().id();
        String text = message.text();

        // Проверяем, есть ли в БД запись для данного чата
        ConstructorDoc constructorDoc = constructorDocRepository.findByChatId(chatId);
        if (constructorDoc == null) {
            // Если записи нет, создаем новую
            constructorDoc = new ConstructorDoc();
            constructorDoc.setChatId(chatId);
            constructorDoc.setState(ConstructorDocState.STEP_1);
            constructorDocRepository.save(constructorDoc);
        }

        // Получаем текущее состояние записи
        ConstructorDocState currentState = constructorDoc.getState();

        // Обрабатываем текущее состояние записи
        switch (currentState) {
            case STEP_1:
                logger.info("step1");
                sendMessage(chatId, "Введите название суда");
                // Задаем первый вопрос и переходим к следующему состоянию
                constructorDoc.setTextCourtName(text);
                constructorDoc.setState(ConstructorDocState.STEP_2);
                constructorDocRepository.save(constructorDoc);
                break;
            case STEP_2:
                logger.info("step2");
                sendMessage(chatId, "Введите адрес суда");
                // Задаем второй вопрос и переходим к следующему состоянию
                constructorDoc.setTextCourtAddress(text);
                constructorDoc.setState(ConstructorDocState.STEP_3);
                constructorDocRepository.save(constructorDoc);
                break;
            case STEP_3:
                logger.info("step3");
                // Задаем третий вопрос и переходим к следующему состоянию
                constructorDoc.setApplicantName(text);
                constructorDoc.setState(ConstructorDocState.COMPLETE);
                constructorDocRepository.save(constructorDoc);
                sendMessage(chatId, "Спасибо! Ваши данные были сохранены.");
                break;
            default:
                logger.info("unknown state");
                sendMessage(chatId, "Извините, я не могу обработать ваш запрос.");
                break;
        }
        constructorDocRepository.save(constructorDoc);
    }

    private void sendMessage(long chatId, String text) {
        bot.execute(new SendMessage(chatId, text));
    }
}