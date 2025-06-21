package org.example.gradingcenter.web.view.model;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MarkViewModel {

    private Long id;

    @Positive(message = "Subject Id must be a positive number")
    private Long subjectId;

    @DecimalMin(value = "2.00", message = "Lowest mark is 2.00")
    @DecimalMax(value = "6.00", message = "Highest mark is 6.00")
    private Double mark;

    @NotNull(message = "Subject is mandatory")
    private String subjectName;

    private String teacherName;

    @NotNull(message = "Teacher is mandatory")
    @Positive(message = "Teacher Id must be a positive number")
    private Long teacherId;

}
