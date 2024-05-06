package com.quize.services.impl;

import com.quize.entities.Quiz;
import com.quize.repositroies.QuizRepositories;
import com.quize.services.QuizService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {

    private QuizRepositories quizRepo;

    public QuizServiceImpl(QuizRepositories quizRepositories){
        this.quizRepo = quizRepositories;
    }

    @Override
    public Quiz add(Quiz quiz) {
        return quizRepo.save(quiz);
    }

    @Override
    public List<Quiz> get() {
        return quizRepo.findAll();
    }

    @Override
    public Quiz get(Long id) {
        return quizRepo.findById(id).orElseThrow(() -> new RuntimeException("Quiz not found."));
    }
}
