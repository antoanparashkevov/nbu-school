package org.example.gradingcenter.web.view.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgramSlotViewModel {

    private String dayOfWeek;

    private int subjectSequence;

    private String subjectName;

    private String teacherName;

}
