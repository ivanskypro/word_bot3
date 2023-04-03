-- liquibase formatted sql

-- changeset pecheneg:1

CREATE TABLE IF NOT EXISTS constructor_doc_table
(
    id                 SERIAL PRIMARY KEY,
    idChat             BIGINT,
    textCourtName      VARCHAR(255),
    textCourtAddress   VARCHAR(255),
    applicantName      VARCHAR(255),
    applicantAddress   VARCHAR(255),
    innNumberApplicant VARCHAR(255),
    defendantName      VARCHAR(255),
    innNumberDefendant VARCHAR(255),
    defendantAddress   VARCHAR(255),
    caseNumber         VARCHAR(255),
    dateCourt          VARCHAR(255),
    timeCourt          VARCHAR(255),
    reason_1           VARCHAR(255)
);