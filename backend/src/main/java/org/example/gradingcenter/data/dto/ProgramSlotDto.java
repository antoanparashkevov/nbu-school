package org.example.gradingcenter.data.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgramSlotDto {

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Min(1)
    @Max(7)
    private int subjectSequence;

    private GradeDto grade;

    private SubjectOutDto subject;

    private String teacherFirstName;

    private String teacherLastName;

}
