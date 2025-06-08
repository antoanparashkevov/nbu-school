package org.example.gradingcenter.web.view.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.gradingcenter.data.dto.GradeDto;
import org.example.gradingcenter.data.dto.SchoolDto;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentViewModel {

    private long id;

    private String firstName;

    private String lastName;

    private Integer absences;

    private String gradeName;

    private Long schoolId;

    private String schoolName;

}
