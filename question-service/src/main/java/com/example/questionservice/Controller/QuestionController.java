package com.example.questionservice.Controller;

import com.example.questionservice.Service.QuestionService;
import com.example.questionservice.entity.QuestionWrapper;
import com.example.questionservice.entity.Response;
import com.example.questionservice.entity.question;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("question")
@Tag(name = "Question Controller", description = "Controller to manage Question service related operations")
public class QuestionController
{
    @Autowired
    QuestionService questionService;

    @Autowired
    Environment environment;


    @GetMapping("GetAllQuestions")
    @Operation(summary = "Gets all Questions", description = "Gets all the questions for User")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "Not Found")
     public ResponseEntity<List<question>> getAllQuestions()
     {
         try
         {
             return new ResponseEntity<>(questionService.getAllQuestions(), HttpStatus.OK);
         }
         catch (Exception e)
         {
             e.printStackTrace();
         }

         return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
     }


     @GetMapping("GetByCategory/{category}")
     @Operation(summary = "Gets questions by category", description = "Gets the questions for User on basis of category passed")
     @ApiResponse(responseCode = "200", description = "OK")
     @ApiResponse(responseCode = "404", description = "Not Found")
     public ResponseEntity<List<question>> getQuestionsByCategory(@PathVariable String category)
     {
         try
         {
             return new ResponseEntity<>(questionService.getQuestionsByCategory(category), HttpStatus.OK);
         }
         catch (Exception e)
         {
             e.printStackTrace();
         }

         return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
     }


     @PostMapping("AddQuestion")
     @Operation(summary = "Add Questions", description = "Adds a new question for User")
     @ApiResponse(responseCode = "201", description = "Created")
     @ApiResponse(responseCode = "400", description = "Bad Request")
     public ResponseEntity<String> addQuestion(@RequestBody question question)
     {
         questionService.addQuestion(question);
         return new ResponseEntity<>("success", HttpStatus.CREATED);
     }

     @GetMapping("generate")
     public ResponseEntity<List<Integer>> getQuestionsForQuiz
             (@RequestParam String categoryName, @RequestParam Integer numQuestions)
     {
         return questionService.getQuestionForQuiz(categoryName, numQuestions);
     }

     @PostMapping("getQuestions")
     public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds)
     {
         System.out.println(environment.getProperty("local.server.port"));
         return questionService.getQuestionsFromId(questionIds);
     }

     @PostMapping("getScore")
     public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses)
     {
         return questionService.getScore(responses);
     }
}
