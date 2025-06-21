package org.example.gradingcenter.web.view.model;

import java.util.List;

import jakarta.validation.constraints.*;
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

    @Size(min = 3, max = 30, message="Last name must be between 3 and 30 characters")
    private String firstName;

    @Size(min = 3, max = 30, message="Last name must be between 3 and 30 characters")
    private String lastName;

    @Min(value = 0, message = "The value must be a positive integer or zero.")
    private Integer absences;

    @Size(min = 2, max = 3, message="Grade must be between 2 and 3 characters")
    @Pattern(regexp = "^[0-9]+[A-Z]$", message = "Invalid grade format. It should be a number followed by a letter (e.g., 2A, 12B, 10C).")
    private String gradeName;

    @NotNull(message = "School is mandatory")
    @Positive(message = "School Id must be a positive number")
    private Long schoolId;

    private String schoolName;

    private List<Long> parentIds;

}
