package com.dolan.namruev.kemal.word_bot.repository;

import com.dolan.namruev.kemal.word_bot.model.ConstructorDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConstructorDocRepository extends JpaRepository <ConstructorDoc, Long> {
    ConstructorDoc existsConstructorDocByChatId(Long chatId);

    Optional<ConstructorDoc> findByTextCourtName(String courtName);
}
