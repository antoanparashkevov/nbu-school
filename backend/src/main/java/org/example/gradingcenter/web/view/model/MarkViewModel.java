package org.example.gradingcenter.web.view.model;

import lombok.Data;

@Data
public class MarkViewModel {

    private Double mark;

    private String subjectName;

    private String teacherName;

    private Long studentId;

}
