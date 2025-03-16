package org.example.gradingcenter.data.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GradeCreateDto {

    @Pattern(
            regexp = "^(?:[1-9]|1[0-2])[A-Za-z]$",
            message = "Invalid grade name format."
    )
    private String name;

    @NotNull(message = "School Id cannot be null")
    private long schoolId;

}
