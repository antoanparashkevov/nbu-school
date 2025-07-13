package org.example.gradingcenter.data.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.example.gradingcenter.data.entity.Grade;
import org.example.gradingcenter.data.entity.Subject;

import java.time.DayOfWeek;

public class ProgramSlotDto {

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Min(1)
    @Max(7)
    private int subjectSequence;

    private Long gradeName;

    private Long subjectId;

}
