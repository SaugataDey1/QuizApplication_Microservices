package com.example.questionservice.dao;

import com.example.questionservice.entity.question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<question, Integer>
{
    List<question> findByCategory(String category);

    @Query(value = "SELECT q.id FROM questions q Where q.category=:category ORDER BY RANDOM() LIMIT :numQ", nativeQuery = true)
    List<Integer> findRandomQuestionByCategory(@Param("category") String category, @Param("numQ") int numQ);
}
