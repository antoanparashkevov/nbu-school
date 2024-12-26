package org.example.gradingcenter.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.gradingcenter.data.entity.School;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GradeDto {

    private long id;

    private String name;

    private School school;

}
