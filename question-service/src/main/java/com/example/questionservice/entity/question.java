package com.example.questionservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name="questions")
public class question
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Question Title cannot be null or blank")
    private String questionTitle;

    @NotEmpty(message = "Option 1 cannot be null or blank")
    private String option1;

    @NotEmpty(message = "Option 2 cannot be null or blank")
    private String option2;

    @NotEmpty(message = "Option 3 cannot be null or blank")
    private String option3;

    @NotEmpty(message = "Option 4 cannot be null or blank")
    private String option4;

    @NotEmpty(message = "Right Answer cannot be null or blank")
    private String rightAnswer;

    private String difficultylevel;

    @NotEmpty(message = "Category cannot be null or blank")
    private String category;
}
