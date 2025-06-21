package org.example.gradingcenter.data.dto.users;

import jakarta.validation.constraints.*;
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

    @NotNull(message = "User Id is mandatory")
    @Positive(message = "User Id must be a positive number")
    private Long userId;

    @Size(min = 3, max = 30, message="Last name must be between 3 and 30 characters")
    private String firstName;

    @Size(min = 3, max = 30, message="Last name must be between 3 and 30 characters")
    private String lastName;

    @Min(value = 0, message = "The value must be a positive integer or zero.")
    private int absences;

    private List<Long> parentIds;

    @Size(min = 2, max = 3, message="Grade must be between 2 and 3 characters")
    @Pattern(regexp = "^[0-9]+[A-Z]$", message = "Invalid grade format. It should be a number followed by a letter (e.g., 2A, 12B, 10C).")
    private String gradeName;

    @NotNull(message = "School is mandatory")
    @Positive(message = "School Id must be a positive number")
    private Long schoolId;

    public StudentInDto(Long userId, Long schoolId) {
        this.userId = userId;
        this.schoolId = schoolId;
    }
}
