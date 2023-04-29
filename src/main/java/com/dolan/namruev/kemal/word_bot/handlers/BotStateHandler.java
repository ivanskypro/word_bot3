package com.dolan.namruev.kemal.word_bot.handlers;

import com.dolan.namruev.kemal.word_bot.cache.BotStatesDataCache;
import com.dolan.namruev.kemal.word_bot.constants.Constants;
import com.dolan.namruev.kemal.word_bot.model.LawDocMaker;
import com.dolan.namruev.kemal.word_bot.model.botStates;
import com.dolan.namruev.kemal.word_bot.builders.KeyboardBuilder;
import com.dolan.namruev.kemal.word_bot.service.ServiceImpl.LawDocMakerServiceImpl;
import com.dolan.namruev.kemal.word_bot.builders.MessageBuilder;
import org.springframework.stereotype.Component;

import static com.dolan.namruev.kemal.word_bot.builders.KeyboardBuilder.cancel;
import static com.dolan.namruev.kemal.word_bot.builders.KeyboardBuilder.chooseOption;

@Component
public class BotStateHandler {
    private final LawDocMakerServiceImpl service;
    private final BotStatesDataCache cacheState;
    private final MessageBuilder messageBuilder;
    private final KeyboardBuilder keyboardBuilder;

    public BotStateHandler(LawDocMakerServiceImpl service, BotStatesDataCache cacheState, MessageBuilder messageBuilder, KeyboardBuilder keyboardBuilder) {
        this.service = service;
        this.cacheState = cacheState;
        this.messageBuilder = messageBuilder;
        this.keyboardBuilder = keyboardBuilder;
    }

    public void handleBotState(Long chatId, LawDocMaker lawDocMaker, String messageText, botStates currentState) {
        botStates newState = switch (currentState) {
            case STATE_START -> handleStartState(chatId);
            case WAITING_OPTION -> handleWaitingOptionState(chatId, lawDocMaker, messageText);
            case ASK_COURT_NAME -> handleAskCourtNameState(chatId, lawDocMaker, messageText);
            case ASK_COURT_ADDRESS -> handleAskCourtAddressState(chatId, lawDocMaker, messageText);
            case ASK_APPLICANT_NAME -> handleAskApplicantNameState(chatId, lawDocMaker, messageText);
            case ASK_APPLICANT_ADDRESS -> handleAskApplicantAddressState(chatId, lawDocMaker, messageText);
            case ASK_INN_APPLICANT -> handleAskApplicantInnState(chatId, lawDocMaker, messageText);
            case ASK_DEFENDANT_NAME -> handleAskDefendantNameState(chatId, lawDocMaker, messageText);
            case ASK_DEFENDANT_ADDRESS -> handleAskDefendantAddressState(chatId, lawDocMaker, messageText);
            case ASK_INN_DEFENDANT -> handleAskDefendantInnState(chatId, lawDocMaker, messageText);
            case ASK_CASE_NUMBER -> handleAskCaseNumberState(chatId, lawDocMaker, messageText);
            case ASK_DATE_COURT -> handleAskDateCourtState(chatId, lawDocMaker, messageText);
            case ASK_TIME_COURT -> handleAskTimeCourtState(chatId, lawDocMaker, messageText);
            case ASK_REASON_ONE -> handleAskReasonOneState(chatId, lawDocMaker, messageText);
            case COMPLETE -> handleMakeDocumentState();
            case STATE_CANCELLED -> handleCancelledState(chatId);
            default -> currentState;
        };
        service.saveResponse(lawDocMaker);
        cacheState.setCurrentConstructorDocState(chatId, newState);
    }

    private botStates handleStartState(Long chatId) {
        messageBuilder.sendMessage(chatId, Constants.GREETINGS, chooseOption());
        return botStates.WAITING_OPTION;
    }

    private botStates handleCancelledState(Long chatId) {
        messageBuilder.sendMessage(chatId, Constants.CHOOSE_OPTION, chooseOption());
        return botStates.WAITING_OPTION;
    }

    private botStates handleWaitingOptionState(Long chatId,
                                               LawDocMaker lawDocMaker,
                                               String text) {
        switch (text) {
            case Constants.ON_ADJOURNMENT -> {
                lawDocMaker.setOption(Constants.ON_ADJOURNMENT);
                messageBuilder.sendMessage(chatId, "Начинаем заполнять ходатайство об отложении судебного заседания!\n" +
                        "\nПожалуйста введите суд", keyboardBuilder.removeOptionsButton());
                return botStates.ASK_COURT_NAME;
            }
            case Constants.FAMILIARIZATION -> {
                lawDocMaker.setOption(Constants.FAMILIARIZATION);
                messageBuilder.sendMessage(chatId, "Начинаем заполнять ходатайство об ознакомлении!\n" +
                        "\nПожалуйста введите суд", keyboardBuilder.removeOptionsButton());
                return botStates.ASK_COURT_NAME;
            }
            default -> {
                messageBuilder.sendMessage(chatId, "Пожалуйста, выберите вариант из списка", chooseOption());
                return botStates.WAITING_OPTION;
            }
        }
    }

    private botStates handleAskCourtNameState(Long chatId, LawDocMaker lawDocMaker, String text) {
        lawDocMaker.setTextCourtName(text);
        messageBuilder.sendMessage(chatId, "Введите адрес суда", cancel());
        return botStates.ASK_COURT_ADDRESS;
    }

    private botStates handleAskCourtAddressState(Long chatId,
                                                 LawDocMaker lawDocMaker,
                                                 String text) {
        lawDocMaker.setTextCourtAddress(text);
        messageBuilder.sendMessage(chatId, "Введите Истца", cancel());
        return botStates.ASK_APPLICANT_NAME;
    }

    private botStates handleAskApplicantNameState(Long chatId,
                                                  LawDocMaker lawDocMaker,
                                                  String text) {
        lawDocMaker.setApplicantName(text);
        messageBuilder.sendMessage(chatId, "Введите адрес Истца", cancel());
        return botStates.ASK_APPLICANT_ADDRESS;
    }

    private botStates handleAskApplicantAddressState(Long chatId,
                                                     LawDocMaker lawDocMaker,
                                                     String text) {
        lawDocMaker.setApplicantAddress(text);
        messageBuilder.sendMessage(chatId, "Введите ИНН Истца", cancel());
        return botStates.ASK_INN_APPLICANT;
    }

    private botStates handleAskApplicantInnState(Long chatId,
                                                 LawDocMaker lawDocMaker,
                                                 String text) {
        lawDocMaker.setInnNumberApplicant(text);
        messageBuilder.sendMessage(chatId, "Введите Ответчика", cancel());
        return botStates.ASK_DEFENDANT_NAME;
    }

    private botStates handleAskDefendantNameState(Long chatId,
                                                  LawDocMaker lawDocMaker,
                                                  String text) {
        lawDocMaker.setDefendantName(text);
        messageBuilder.sendMessage(chatId, "Введите адрес Ответчика", cancel());
        return botStates.ASK_DEFENDANT_ADDRESS;
    }

    private botStates handleAskDefendantAddressState(Long chatId,
                                                     LawDocMaker lawDocMaker,
                                                     String text) {
        lawDocMaker.setDefendantAddress(text);
        messageBuilder.sendMessage(chatId, "Введите ИНН Ответчика", cancel());
        return botStates.ASK_INN_DEFENDANT;
    }

    private botStates handleAskDefendantInnState(Long chatId,
                                                 LawDocMaker lawDocMaker,
                                                 String text) {
        lawDocMaker.setInnNumberDefendant(text);
        messageBuilder.sendMessage(chatId, "Введите номер дела", cancel());
        return botStates.ASK_CASE_NUMBER;
    }

    private botStates handleAskCaseNumberState(Long chatId,
                                               LawDocMaker lawDocMaker,
                                               String text) {
        lawDocMaker.setCaseNumber(text);
        messageBuilder.sendMessage(chatId, "Введите дату когда назначено" +
                " Ваше судебное заседание ", cancel());
        return botStates.ASK_DATE_COURT;
    }

    private botStates handleAskDateCourtState(Long chatId,
                                              LawDocMaker lawDocMaker,
                                              String text) {
        lawDocMaker.setDateCourt(text);
        messageBuilder.sendMessage(chatId, "Введите время когда назначено" +
                " Ваше судебное заседание ", cancel());
        return botStates.ASK_TIME_COURT;
    }

    private botStates handleAskTimeCourtState(Long chatId,
                                              LawDocMaker lawDocMaker,
                                              String text) {
        lawDocMaker.setTimeCourt(text);
        messageBuilder.sendMessage(chatId, "Введите причину для отложения судебного заседания", cancel());
        return botStates.ASK_REASON_ONE;
    }

    private botStates handleAskReasonOneState(Long chatId,
                                              LawDocMaker lawDocMaker,
                                              String text) {
        lawDocMaker.setReason_1(text);
        messageBuilder.sendMessage(chatId, "Мы заполнили Ваше заявление, сейчас произойдет магия \uD83D\uDCAB" +
                " и бот пришлет документ", cancel());
        return botStates.COMPLETE;
    }

    private botStates handleMakeDocumentState() {
        return botStates.SEND_DOCUMENT; // тут должна быть реализация создания документа из шаблона
    }
}