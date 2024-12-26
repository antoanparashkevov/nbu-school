package org.example.gradingcenter.data.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GradeCreateDto {

    @Size(min = 2, max = 2, message = "Name should be 2 symbols")
    private String name;

    @NotNull(message = "School Id cannot be null")
    private long schoolId;

}
