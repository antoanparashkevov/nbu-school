package org.example.gradingcenter.web.view.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SchoolViewModel {

    private long id;

    @NotBlank(message = "School name is required")
    @Size(min = 3, max = 50, message = "School name must be between 3 and 50 characters")
    private String name;

    @NotBlank(message = "Address is required")
    @Size(min = 5, max = 100, message = "Address must be between 5 and 100 characters")
    private String address;

    @NotNull(message = "Headmaster must be selected")
    private Long headmasterId;

}
