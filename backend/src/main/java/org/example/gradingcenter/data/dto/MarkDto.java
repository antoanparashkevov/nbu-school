package org.example.gradingcenter.data.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MarkDto {

    @DecimalMin("2.00")
    @DecimalMax("6.00")
    private double mark;

    private String subjectName;

    private Long studentId;

    private Long teacherId;

}
