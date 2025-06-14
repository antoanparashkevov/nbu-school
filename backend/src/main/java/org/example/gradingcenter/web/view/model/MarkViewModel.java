package org.example.gradingcenter.web.view.model;

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

    private Long subjectId;

    private Double mark;

    private String subjectName;

    private String teacherName;

    private Long teacherId;

    private Long studentId;

}
