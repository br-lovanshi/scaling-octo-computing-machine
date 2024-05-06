package com.quize.repositroies;

import com.quize.entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepositories extends JpaRepository<Quiz, Long> {
}
