package org.example.gradingcenter.data.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MarkDto {

    @DecimalMin(value = "2.00", message = "Lowest mark is 2.00")
    @DecimalMax(value = "6.00", message = "Highest mark is 6.00")
    private double mark;

    @NotNull(message = "Subject is mandatory")
    private String subjectName;

    @NotNull(message = "Student is mandatory")
    @Positive(message = "Student Id must be a positive number")
    private Long studentId;

    @NotNull(message = "Teacher is mandatory")
    @Positive(message = "Teacher Id must be a positive number")
    private Long teacherId;

}
