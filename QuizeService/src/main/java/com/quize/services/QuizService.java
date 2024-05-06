package com.quize.services;

import com.quize.entities.Quiz;
import java.util.List;

public interface QuizService {

    Quiz add(Quiz quiz);

    List<Quiz>  get();

    Quiz get(Long id);
}
