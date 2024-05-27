package com.quize.services;

import com.quize.entities.Question;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "question-service", url = "http://localhost:8081")
public interface QuestionClient {
//    http://127.0.0.1:8081/question/quiz/2
    @GetMapping("/question/quiz/{quizId}")
    List<Question> getQustionOfQuiz(@PathVariable Long quizId);
}
