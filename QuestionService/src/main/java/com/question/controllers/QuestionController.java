package com.question.controllers;

import com.question.entities.Question;
import com.question.services.QuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    QuestionService questionService;

    public  QuestionController(QuestionService questionService){
        this.questionService = questionService;
    }

    @PostMapping
    public Question store(@RequestBody Question question){
        return questionService.add(question);
    }

    @GetMapping
    public List<Question> getAll(){
        return questionService.get();
    }

    @GetMapping("/{id}")
    public Question get(@PathVariable Long id){
        return questionService.get(id);
    }

    @GetMapping("/{quizId}")
    public List<Question> findByQuizId(@PathVariable Long quizId){
        return questionService.getByQuizId(quizId);
    }


}
