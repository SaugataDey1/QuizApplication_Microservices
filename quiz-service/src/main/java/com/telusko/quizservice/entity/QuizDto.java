package com.telusko.quizservice.entity;

import lombok.Data;

@Data
public class QuizDto
{
    String categoryName;
    Integer numQuestions;
    String title;
}
