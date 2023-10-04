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
        if (lawDocMaker.getTextCourtName() != null) {
            values.put("<название суда>", lawDocMaker.getTextCourtName());
        }
        if (lawDocMaker.getTextCourtAddress() != null) {
            values.put("<адрес суда>", lawDocMaker.getTextCourtAddress());
        }
        if (lawDocMaker.getApplicantName() != null) {
            values.put("<название компании или ФИО Истца>", lawDocMaker.getApplicantName());
        }
        if (lawDocMaker.getApplicantAddress() != null) {
            values.put("<адрес истца>", lawDocMaker.getApplicantAddress());
        }
        if (lawDocMaker.getInnNumberApplicant() != null) {
            values.put("<ИНН#1>", lawDocMaker.getInnNumberApplicant());
        }
        if (lawDocMaker.getDefendantName() != null) {
            values.put("<название компании или ФИО Ответчика>", lawDocMaker.getDefendantName());
        }
        if (lawDocMaker.getDefendantAddress() != null) {
            values.put("<адрес ответчика>", lawDocMaker.getDefendantAddress());
        }
        if (lawDocMaker.getInnNumberDefendant() != null) {
            values.put("<ИНН#2>", lawDocMaker.getInnNumberDefendant());
        }
        if (lawDocMaker.getCaseNumber() != null) {
            values.put("<номер дела>", lawDocMaker.getCaseNumber());
        }
        if (lawDocMaker.getDateCourt() != null) {
            values.put("<дата>", lawDocMaker.getDateCourt());
        }
        if (lawDocMaker.getTimeCourt() != null) {
            values.put("<время>", lawDocMaker.getTimeCourt());
        }
        if (lawDocMaker.getReason_1() != null) {
            values.put("<причина>", lawDocMaker.getReason_1());
        }
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