package com.telusko.quizservice.Controller;

import com.telusko.quizservice.Service.QuizService;
import com.telusko.quizservice.entity.QuestionWrapper;
import com.telusko.quizservice.entity.QuizDto;
import com.telusko.quizservice.entity.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
@Tag(name = "Quiz Controller", description = "Controller to manage Quiz service related operations")
public class QuizController
{
    @Autowired
    QuizService quizService;


    @PostMapping("create")
    @Operation(summary = "Create Quiz", description = "Creates a random quiz with user entering number of questions,quiz category and quiz title")
    @ApiResponse(responseCode = "201", description = "Created")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto)
    {
        return quizService.createQuiz(quizDto.getCategoryName(), quizDto.getNumQuestions(), quizDto.getTitle());
    }


    @GetMapping("get/{id}")
    @Operation(summary = "Get Quiz", description = "Gets the quiz for user based on quiz id")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "Not Found")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id)
    {
        return quizService.getQuizQuestions(id);
    }


    @PostMapping("submit/{id}")
    @Operation(summary = "Get Quiz Response", description = "Gets the response in form of total number of correct questions attempted by user")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "Not Found")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses)
    {
        return quizService.calculateResult(id, responses);
    }
}
