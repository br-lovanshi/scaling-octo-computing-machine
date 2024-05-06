package com.quize.controllers;

import com.quize.QuizeServiceApplication;
import com.quize.entities.Quiz;
import com.quize.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {


    private QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

//    create quiz
    @PostMapping
    public Quiz create(@RequestBody Quiz quiz){
        return quizService.add(quiz);
    }

    @GetMapping
    public List<Quiz> get(){
        return quizService.get();
    }

    @GetMapping("/{id}")
    public Quiz getOne(@PathVariable  Long id){
        return quizService.get(id);
    }
}
