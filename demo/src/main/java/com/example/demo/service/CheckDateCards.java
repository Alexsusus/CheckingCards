package com.example.demo.service;

import com.example.demo.models.Card;
import com.example.demo.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@EnableScheduling
@Service
public class CheckDateCards {

    @Autowired
    CardRepository cardRepository;

    @Async
    public Future<List<Card>> searchExpiredCards() {
        LocalDate localDate = LocalDate.now();
        List<Card> cardsList = cardRepository.findAllByDateBefore(localDate);
        return new AsyncResult<>(cardsList);
    }

    @Scheduled(fixedRate = 3000) //(cron = "0 0/30 8 * * *") every day at 8 a.m.
    @Async
    public void start() {
        Future<List<Card>> future = searchExpiredCards();
        try (FileWriter writer = new FileWriter("ExpiredCards.txt", false)) {
            List<Card> cardList = future.get();
            System.err.println(cardList.size());
            for (int i = 0; i < cardList.size(); i++) {
                writer.write(cardList.get(i).toString() + "\n");
            }

        } catch (IOException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
