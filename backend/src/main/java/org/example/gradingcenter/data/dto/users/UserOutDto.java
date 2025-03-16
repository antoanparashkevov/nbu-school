package org.example.gradingcenter.data.dto.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserOutDto {

    private long id;

    private String firstName;

    private String lastName;

    private String username;

    private Set<String> authorities;

}
