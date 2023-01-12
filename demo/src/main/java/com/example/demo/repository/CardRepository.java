package com.example.demo.repository;


import com.example.demo.models.Card;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface CardRepository extends CrudRepository<Card, Integer> {
    List<Card>findAllByDateBefore(LocalDate date);
}
