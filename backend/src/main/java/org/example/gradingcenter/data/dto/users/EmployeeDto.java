package org.example.gradingcenter.data.dto.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.gradingcenter.data.dto.SchoolOutDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto extends UserOutDto {

    private SchoolOutDto school;

    public Long getSchoolId() {
        if (school == null) {
            return null;
        }
        return school.getId();
    }

    public String getSchoolName() {
        if (school == null) {
            return null;
        }
        return school.getName();
    }

}
