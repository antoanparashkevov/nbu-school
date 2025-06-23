package org.example.gradingcenter.data.dto.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherInDto {

    private Long userId;

    private Long schoolId;

    private String firstName;

    private String lastName;

    public TeacherInDto(Long userId, Long schoolId) {
        this.schoolId = schoolId;
        this.userId = userId;
    }
}
