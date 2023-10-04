package com.dolan.namruev.kemal.word_bot.constants;

public interface Constants {
    String START_CMD = "/start";
    String CANCEL_CMD = "/cancel";
    String GREETINGS = "Привет \uD83D\uDC4B\uD83C\uDFFB" +
            "\nЯ могу создать для Вас разные ходатайства" +
            "\nДавайте выберем какой документ Вы хотите создать!⬇️";
    String CHOOSE_OPTION = "\nДавайте выберем какой документ Вы хотите создать!⬇️";
    String ON_ADJOURNMENT = "Ходатайство об отложении судебного заседания";
    String FAMILIARIZATION = "Ходатайство об ознакомлении с материалами дела";
    String CANCELLED_TEXT = "\uD83D\uDD01 Начать сначала";
    String GET_DOCUMENT = "✅ Получить документ";
    String TEMPLATE_PATH = "src/main/motions/";
    String OUTPUT_PATH = "src/main/completed_documents/";
}