package org.example.gradingcenter.web.view.model;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParentViewModel {

    private Long id;

    @Size(min = 3, max = 30, message = "First name must be between 3 and 30 characters")
    private String firstName;

    @Size(min = 3, max = 30, message = "Last name must be between 3 and 30 characters")
    private String lastName;

    private Integer numberOfChildren;

}
