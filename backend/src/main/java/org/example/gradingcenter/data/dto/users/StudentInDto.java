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
public class StudentInDto {

    private Long userId;

    private Integer absences;

    private List<Long> parentIds;

    private String gradeName;

    private Long schoolId;

    public StudentInDto(Long userId, Long schoolId) {
        this.userId = userId;
        this.schoolId = schoolId;
    }
}
