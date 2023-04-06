package com.dolan.namruev.kemal.word_bot.service.ServiceImpl;

import com.dolan.namruev.kemal.word_bot.cache.ConstructorDocDataCache;
import com.dolan.namruev.kemal.word_bot.constants.Constants;
import com.dolan.namruev.kemal.word_bot.model.ConstructorDoc;
import com.dolan.namruev.kemal.word_bot.model.ConstructorDocState;
import com.dolan.namruev.kemal.word_bot.service.ConstructorDocService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageHandlerImpl {
    private final ConstructorDocService constructorDocService;
    private final TelegramBot bot;
    private final ConstructorDocDataCache cacheState;
    private final ConstructorDoc constructorDoc = new ConstructorDoc();

    @Autowired
    public MessageHandlerImpl(ConstructorDocService constructorDocService, TelegramBot bot, ConstructorDocDataCache cacheState) {
        this.constructorDocService = constructorDocService;
        this.bot = bot;
        this.cacheState = cacheState;
    }

    public void handle(Message message) throws IllegalStateException {
        Long chatId = message.chat().id();
        String text = message.text();
        handleConstructorDocState(chatId, constructorDoc, text, cacheState.getConstructorDocState(chatId));
    }

    private void handleConstructorDocState(long chatId, ConstructorDoc constructorDoc,
                                           String text, ConstructorDocState state) {
        if (text.equals(Constants.START)) {
            cacheState.setCurrentConstructorDocState(chatId, ConstructorDocState.STATE_START);
        }
        if (text.equals(Constants.CANCELLED)) {
            cacheState.setCurrentConstructorDocState(chatId, ConstructorDocState.STATE_CANCELLED);
        }
        switch (state) {
            case STATE_START -> {
                sendMessage(chatId, Constants.GREETINGS, chooseOption());
                cacheState.setCurrentConstructorDocState(chatId, ConstructorDocState.WAITING_OPTION);
            }
            case WAITING_OPTION -> {
                if (text.equals(Constants.ON_ADJOURNMENT)) {
                    constructorDoc.setOption(Constants.ON_ADJOURNMENT);
                    cacheState.setCurrentConstructorDocState(chatId, ConstructorDocState.ASK_COURT_NAME);
                    sendMessage(chatId, "Начинаем заполнять ходатайство об отложении судебного заседания!\n" +
                            "\nПожалуйста введите суд");
                }
                if (text.equals(Constants.FAMILIARIZATION)) {
                    constructorDoc.setOption(Constants.FAMILIARIZATION);
                    cacheState.setCurrentConstructorDocState(chatId, ConstructorDocState.ASK_COURT_NAME);
                    sendMessage(chatId, "Начинаем заполнять ходатайство об ознакомлении!\n" +
                            "\nПожалуйста введите суд");
                }
            }
            case ASK_COURT_NAME -> {
                constructorDoc.setTextCourtName(text);
                cacheState.setCurrentConstructorDocState(chatId, ConstructorDocState.ASK_COURT_ADDRESS);
                sendMessage(chatId, "Введите адрес суда", cancelButton());
            }
            case ASK_COURT_ADDRESS -> {
                constructorDoc.setTextCourtAddress(text);
                cacheState.setCurrentConstructorDocState(chatId, ConstructorDocState.ASK_APPLICANT_NAME);
                sendMessage(chatId, "Введите Истца", cancelButton());
            }
            case ASK_APPLICANT_NAME -> {
                constructorDoc.setApplicantName(text);
                cacheState.setCurrentConstructorDocState(chatId, ConstructorDocState.COMPLETE);
                sendMessage(chatId, "Данные сохранены", cancelButton());
            }
            case STATE_CANCELLED -> {
                cacheState.setCurrentConstructorDocState(chatId, ConstructorDocState.STATE_START);
            }
            case COMPLETE -> sendMessage(chatId, "делаю док");
            default -> sendMessage(chatId, "состояние говно");
        }
        constructorDocService.saveResponse(constructorDoc);
    }
    private void reset(long chatId, ConstructorDoc constructorDoc, ConstructorDocDataCache cacheState) {
        constructorDocService.reset(chatId); // сброс всех заполненных данных
        cacheState.setCurrentConstructorDocState(chatId, ConstructorDocState.STATE_START); // установка начального состояния
        cacheState.clearConstructorDocState(chatId); // очистка кэша состояния
        sendMessage(chatId, "Заполнение данных сброшено. Вы можете начать заново, отправив команду /start");
    }

    private void sendMessage(long chatId, String text) {
        bot.execute(new SendMessage(chatId, text));
    }

    public void sendMessage(Long chatId, String inputMessage, Keyboard keyboard) {
        SendMessage outputMessage = new SendMessage(chatId, inputMessage)
                .replyMarkup(keyboard);
        try {
            bot.execute(outputMessage);
        } catch (Exception e) {
            log.info("Exception was thrown in sendMessage method with keyboard ");
            e.printStackTrace();
        }
    }
    private static ReplyKeyboardMarkup chooseOption() {
        log.info("Choose keyboard was called");
        return new ReplyKeyboardMarkup(
                Constants.ON_ADJOURNMENT, Constants.FAMILIARIZATION)
                .resizeKeyboard(true)
                .selective(true).oneTimeKeyboard(true).inputFieldPlaceholder("");
    }
    public static ReplyKeyboardMarkup cancelButton() {
        log.info("Cancel keyboard was called");
        KeyboardButton resetButton = new KeyboardButton(Constants.CANCELLED);
        return new ReplyKeyboardMarkup(resetButton).resizeKeyboard(true)
                .selective(true).oneTimeKeyboard(true);
    }
}