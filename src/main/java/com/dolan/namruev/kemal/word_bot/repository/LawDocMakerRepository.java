package com.dolan.namruev.kemal.word_bot.repository;

import com.dolan.namruev.kemal.word_bot.model.LawDocMaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LawDocMakerRepository extends JpaRepository<LawDocMaker, Long> {
    LawDocMaker findByChatId(long chatId);
}