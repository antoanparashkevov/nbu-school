package org.example.gradingcenter.web.view.model;

import jakarta.validation.constraints.*;
import lombok.*;
import org.example.gradingcenter.data.entity.enums.Roles;

import java.util.Set;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignupViewModel {

    @NotBlank(message = "EGN can not be empty")
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid EGN format.")
    private String egn;

    @NotBlank
    @Size(min = 3, max = 20, message="First name must be between 3 and 20 characters")
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 20, message="Last name must be between 3 and 20 characters")
    private String lastName;

    @NotBlank
    @Size(min = 3, max = 20, message="Username must be between 3 and 20 characters")
    private String username;

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 chars long, contain uppercase, lowercase, digit, and special character."
    )
    private String password;

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 chars long, contain uppercase, lowercase, digit, and special character."
    )
    private String confirmPassword;

    @NotEmpty(message = "Select at least one role")
    private Set<String> roles;

    private Long schoolId;

}
