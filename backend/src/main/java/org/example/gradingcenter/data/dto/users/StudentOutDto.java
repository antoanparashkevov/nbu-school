package org.example.gradingcenter.data.dto.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentOutDto extends UserOutDto {

    private Integer absences;

    private List<Long> parentIds;

    private String gradeName;

    private String schoolName;

}
