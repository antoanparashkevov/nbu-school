package org.example.gradingcenter.data.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class ProgramDto {

    private Long id;

    @NotNull(message = "Subject is mandatory")
    private List<String> subjectName;

}
