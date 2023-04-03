package com.dolan.namruev.kemal.word_bot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "constructor_doc_table")
@Setter
@Getter
@NoArgsConstructor
public class ConstructorDoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long chatId;
    private String textCourtName;
    private String textCourtAddress;
    private String applicantName;
    private String applicantAddress;
    private String innNumberApplicant;
    private String defendantName;
    private String defendantAddress;
    private String innNumberDefendant;
    private String caseNumber;
    private String dateCourt;
    private String timeCourt;
    private String reason_1;
    private ConstructorDocState state;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConstructorDoc that = (ConstructorDoc) o;
        return chatId == that.chatId
                && Objects.equals(textCourtName, that.textCourtName)
                && Objects.equals(textCourtAddress, that.textCourtAddress)
                && Objects.equals(applicantName, that.applicantName)
                && Objects.equals(applicantAddress, that.applicantAddress)
                && Objects.equals(innNumberApplicant, that.innNumberApplicant)
                && Objects.equals(defendantName, that.defendantName)
                && Objects.equals(innNumberDefendant, that.innNumberDefendant)
                && Objects.equals(defendantAddress, that.defendantAddress)
                && Objects.equals(caseNumber, that.caseNumber)
                && Objects.equals(dateCourt, that.dateCourt)
                && Objects.equals(timeCourt, that.timeCourt)
                && Objects.equals(reason_1, that.reason_1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId,
                textCourtName,
                textCourtAddress,
                applicantName,
                applicantAddress,
                innNumberApplicant,
                defendantName,
                innNumberDefendant,
                defendantAddress,
                caseNumber,
                dateCourt,
                timeCourt,
                reason_1);
    }

    @Override
    public String toString() {
        return "ConstructorDoc{" +
                "chatId=" + chatId +
                ", textCourtName='" + textCourtName + '\'' +
                ", textCourtAddress='" + textCourtAddress + '\'' +
                ", applicantName='" + applicantName + '\'' +
                ", applicantAddress='" + applicantAddress + '\'' +
                ", innNumberApplicant='" + innNumberApplicant + '\'' +
                ", defendantName='" + defendantName + '\'' +
                ", innNumberDefendant='" + innNumberDefendant + '\'' +
                ", defendantAddress='" + defendantAddress + '\'' +
                ", caseNumber='" + caseNumber + '\'' +
                ", dateCourt='" + dateCourt + '\'' +
                ", timeCourt='" + timeCourt + '\'' +
                ", reason_1='" + reason_1 + '\'' +
                '}';
    }
}

