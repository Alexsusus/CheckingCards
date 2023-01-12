package com.example.demo.controllers;

import com.example.demo.models.Card;
import com.example.demo.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@org.springframework.stereotype.Controller
public class MainController {
    @Autowired
    CardRepository cardRepository;

    @GetMapping("/")
    public String load(){
        return "redirect:home";
    }

    @GetMapping("home")
    public String home(Model model) {
        Iterable<Card> cardsIt = cardRepository.findAll();
        model.addAttribute("cards", cardsIt);
        return "home";
    }
    @PostMapping("addCardForm")
    public String addCardForm(@ModelAttribute Card card) {
        System.err.println(card);
        cardRepository.save(card);
        return "redirect:home";
    }
}
