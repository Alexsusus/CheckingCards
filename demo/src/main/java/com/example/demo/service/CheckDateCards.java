package com.example.demo.service;

import com.example.demo.models.Card;
import com.example.demo.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Future;

public class CheckDateCards {

    @Autowired
    CardRepository cardRepository;

    @Async
    public Future<List<Card>> checkDate() {
        List<Card> cardList = (List<Card>) cardRepository.findAll();
        System.err.println(cardList);
        return new AsyncResult<>(cardList);
    }

    @Async
    public void start() {
        try {
            Future<List<Card>> future = checkDate();
            LocalDate localDate = LocalDate.now();
            while (!future.isDone()) {
                Thread.sleep(10);
                List<Card> cards = future.get();
                for (Card card : cards) {
                    if (card.getDate() == localDate){
                        System.err.println(card);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
