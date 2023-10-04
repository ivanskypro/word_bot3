package com.dolan.namruev.kemal.word_bot.builders;

import com.dolan.namruev.kemal.word_bot.constants.Constants;
import com.pengrad.telegrambot.model.request.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KeyboardBuilder {
    public static ReplyKeyboardMarkup chooseOption() {
        log.info("Choose keyboard was called");
        KeyboardButton button1 = new KeyboardButton(Constants.ON_ADJOURNMENT);
        KeyboardButton button2 = new KeyboardButton(Constants.FAMILIARIZATION);

        return new ReplyKeyboardMarkup(new KeyboardButton[][]{{button1}, {button2}})
                .resizeKeyboard(true)
                .selective(true)
                .oneTimeKeyboard(true)
                .inputFieldPlaceholder(" ");
    }

    public Keyboard removeOptionsButton() {
        return new ReplyKeyboardRemove(true);
    }

    public InlineKeyboardMarkup finalButtons() {
        InlineKeyboardButton cancelButton = new InlineKeyboardButton(Constants.CANCELLED_TEXT);
        cancelButton.callbackData(Constants.CANCELLED_TEXT);

        InlineKeyboardButton getDocumentButton = new InlineKeyboardButton(Constants.GET_DOCUMENT);
        getDocumentButton.callbackData(Constants.GET_DOCUMENT);

        return new InlineKeyboardMarkup(getDocumentButton, cancelButton);
    }

    public static ReplyKeyboardMarkup cancel() {
        log.info("Choose keyboard was called");
        KeyboardButton button1 = new KeyboardButton(Constants.CANCELLED_TEXT);
        return new ReplyKeyboardMarkup(new KeyboardButton[][]{{button1}})
                .resizeKeyboard(true)
                .selective(true)
                .oneTimeKeyboard(true)
                .inputFieldPlaceholder(" ");
    }
}