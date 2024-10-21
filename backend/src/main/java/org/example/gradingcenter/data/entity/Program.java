package org.example.gradingcenter.data.entity;

import jakarta.persistence.*;
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
@Entity
public class Program extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek dayOfWeek;

    @Min(1)
    @Max(7)
    @Column(nullable = false)
    private int subjectSequence;

    @ManyToOne(optional = false)
    private Grade grade;

    @ManyToOne(optional = false)
    private Subject subject;
}
