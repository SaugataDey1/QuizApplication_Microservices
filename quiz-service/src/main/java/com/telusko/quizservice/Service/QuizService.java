package com.telusko.quizservice.Service;

import com.telusko.quizservice.Exception.ResourceNotFoundException;
import com.telusko.quizservice.dao.QuizDao;
import com.telusko.quizservice.entity.QuestionWrapper;
import com.telusko.quizservice.entity.Quiz;
import com.telusko.quizservice.entity.Response;
import com.telusko.quizservice.feign.QuizInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class QuizService
{
    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizInterface quizInterface;

   // @Autowired
   // QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title)
    {
       List<Integer> questions = quizInterface.getQuestionsForQuiz(category, numQ).getBody();

       Quiz quiz = new Quiz();
       quiz.setTitle(title);
       quiz.setQuestionIds(questions);

       quizDao.save(quiz);

       return new ResponseEntity<>("Created Quiz!", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id)
    {
        Quiz quiz = quizDao.findById(id).get();
        List<Integer> questionIds = quiz.getQuestionIds();

        ResponseEntity<List<QuestionWrapper>> questions = quizInterface.getQuestionsFromId(questionIds);

        return questions;



        /*
        if(quiz.isPresent())
            quiz = optionalQuiz.get();
        else {
            log.error("Quiz not found....");
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND,"Quiz with the given Id not found");
        }

        if(quiz.getQuestionIds().isEmpty()) {
            log.error("Quiz has no questions...");
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND,"Quiz is empty without any questions");
        }

         */

      //  List<question> questionsFromDB = quiz.getQuestions();
      //  List<QuestionWrapper> questionsForUser = new ArrayList<>();
      /*  for(question q : questionsFromDB)
        {
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            questionsForUser.add(qw);
        }

       */


    }


    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses)
    {
        ResponseEntity<Integer> score = quizInterface.getScore(responses);

        return score;
    }
}
