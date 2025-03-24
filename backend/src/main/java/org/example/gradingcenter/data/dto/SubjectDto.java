package org.example.gradingcenter.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.gradingcenter.data.entity.enums.SubjectName;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDto {

    private SubjectName name;

    private long teacherId;

    private String gradeName;

    private Long schoolId;

}
