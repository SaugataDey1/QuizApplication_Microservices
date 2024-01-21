package com.example.questionservice.Service;

import com.example.questionservice.Exception.ResourceNotFoundException;
import com.example.questionservice.dao.QuestionDao;
import com.example.questionservice.entity.QuestionWrapper;
import com.example.questionservice.entity.Response;
import com.example.questionservice.entity.question;
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
public class QuestionService
{
    @Autowired
    QuestionDao questionDao;

    public List<question> getAllQuestions()
    {
        if(questionDao.findAll().isEmpty())
        {
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "No Questions Found to be displayed");
        }
        return questionDao.findAll();
    }


    public List<question> getQuestionsByCategory(String category)
    {
        if(questionDao.findByCategory(category).isEmpty())
        {
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "No Questions are available for the given category: "+ category);
        }
        return questionDao.findByCategory(category);
    }


    public String addQuestion(question question)
    {
        questionDao.save(question);
        return "success";
    }

    public ResponseEntity<List<Integer>> getQuestionForQuiz(String categoryName, Integer numQuestions)
    {
        List<Integer> questions = questionDao.findRandomQuestionByCategory(categoryName, numQuestions);

        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds)
    {
        List<QuestionWrapper> wrappers = new ArrayList<>();
        List<question> questions = new ArrayList<>();

        for(Integer id : questionIds)
        {
            questions.add(questionDao.findById(id).get());
        }

        for(question q : questions)
        {
            QuestionWrapper wrapper = new QuestionWrapper();
            wrapper.setId(q.getId());
            wrapper.setQuestionTitle(q.getQuestionTitle());
            wrapper.setOption1(q.getOption1());
            wrapper.setOption2(q.getOption2());
            wrapper.setOption3(q.getOption3());
            wrapper.setOption4(q.getOption4());

            wrappers.add(wrapper);
        }

        return new ResponseEntity<>(wrappers, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses)
    {
        int right = 0;
        for(Response response : responses)
        {
            if(response.getResponse()==null||response.getResponse().isEmpty()) {
                log.error("Empty Response Body....");
                throw new ResourceNotFoundException(HttpStatus.BAD_REQUEST, "Response is empty");
            }

            question q = questionDao.findById(response.getId()).get();

            if(response.getResponse().equals(q.getRightAnswer()))
                right++;

        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}

// generate
// getQuestions (questionid)
// getScore
