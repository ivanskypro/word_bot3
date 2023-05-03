package com.dolan.namruev.kemal.word_bot.builders;

import com.dolan.namruev.kemal.word_bot.constants.Constants;
import com.dolan.namruev.kemal.word_bot.model.LawDocMaker;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class LawDocBuilder {

    public void generateDocument(LawDocMaker lawDocMaker) throws IOException {
        String inputFileName = lawDocMaker.getOption() + ".docx";
        String outputFileName = lawDocMaker.getOption() + ".docx";

        // Загрузка шаблона из файла
        InputStream inputStream = new FileInputStream(Constants.TEMPLATE_PATH+inputFileName);
        XWPFDocument document = new XWPFDocument(inputStream);

        // Получение значений полей из объекта LawDocMaker
        Map<String, String> values = new HashMap<>();
        values.put("<название суда>", lawDocMaker.getTextCourtName());
        values.put("<адрес суда>", lawDocMaker.getTextCourtAddress());
        values.put("<название компании или ФИО Истца>", lawDocMaker.getApplicantName());
        values.put("<адрес истца>", lawDocMaker.getApplicantAddress());
        values.put("<ИНН#1>", lawDocMaker.getInnNumberApplicant());
        values.put("<название компании или ФИО Ответчика>", lawDocMaker.getDefendantName());
        values.put("<адрес ответчика>", lawDocMaker.getDefendantAddress());
        values.put("<ИНН#2>", lawDocMaker.getInnNumberDefendant());
        values.put("<номер дела>", lawDocMaker.getCaseNumber());
        values.put("<дата>", lawDocMaker.getDateCourt());
        values.put("<время>", lawDocMaker.getTimeCourt());
        values.put("<причина>", lawDocMaker.getReason_1());

        for (XWPFParagraph paragraph : document.getParagraphs()) {
            for (XWPFRun run : paragraph.getRuns()) {
                String text = run.getText(0);
                if (text != null) {
                    for (Map.Entry<String, String> entry : values.entrySet()) {
                        String placeholder = entry.getKey();
                        String value = entry.getValue();
                        if (text.contains(placeholder)) {
                            text = text.replace(placeholder, value);
                            run.setText(text, 0);
                        }
                    }
                }
            }
        }
        document.write(new FileOutputStream(Constants.OUTPUT_PATH+outputFileName));
    }
}