package com.quize.services.impl;

import com.quize.entities.Quiz;
import com.quize.repositroies.QuizRepositories;
import com.quize.services.QuestionClient;
import com.quize.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {

    private QuizRepositories quizRepo;

    private QuestionClient questionClient;

    public QuizServiceImpl(QuizRepositories quizRepo, QuestionClient questionClient) {
        this.quizRepo = quizRepo;
        this.questionClient = questionClient;
    }

    @Override
    public Quiz add(Quiz quiz) {
        return quizRepo.save(quiz);
    }

    @Override
    public List<Quiz> get() {
        List<Quiz> quizzes =  quizRepo.findAll();

        List<Quiz> quizzessWithQuestion = quizzes.stream().map(quiz -> {
           quiz.setQuestions(questionClient.getQustionOfQuiz(quiz.getId()));
           return quiz;
        }).collect(Collectors.toList());

        return quizzessWithQuestion;
    }

    @Override
    public Quiz get(Long id) {
        Quiz quiz =  quizRepo.findById(id).orElseThrow(() -> new RuntimeException("Quiz not found."));

        quiz.setQuestions(questionClient.getQustionOfQuiz(quiz.getId()));

        return quiz;
    }
}
