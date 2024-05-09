package com.quize.services;

import com.quize.entities.Question;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "question-service", url = "http://localhost:9090")
public interface QuestionClient {

    @GetMapping("/questions/{quizId}")
    List<Question> getQustionOfQuiz(@PathVariable Long quizId);
}
