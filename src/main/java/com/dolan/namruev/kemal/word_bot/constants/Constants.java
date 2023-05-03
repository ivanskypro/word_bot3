package com.dolan.namruev.kemal.word_bot.constants;

import com.dolan.namruev.kemal.word_bot.model.LawDocMaker;

public interface Constants {
    String START = "/start";
    String GREETINGS = "Привет \uD83D\uDC4B\uD83C\uDFFB" +
            "\nЯ могу создать для Вас разные ходатайства" +
            "\nДавайте выберем какой документ Вы хотите создать!⬇️";
    String CHOOSE_OPTION = "\nДавайте выберем какой документ Вы хотите создать!⬇️";
    String ON_ADJOURNMENT = "Ходатайство об отложении судебного заседания";
    String FAMILIARIZATION = "Ходатайство об ознакомлении с материалами дела";
    String CANCELLED = "↩️Начать сначала";
    String TEMPLATE_PATH = "src/main/motions/";
    String OUTPUT_PATH = "src/main/completed_documents/";
}