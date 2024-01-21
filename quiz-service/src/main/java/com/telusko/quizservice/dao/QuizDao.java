package com.telusko.quizservice.dao;

import com.telusko.quizservice.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizDao extends JpaRepository<Quiz, Integer>
{

}
