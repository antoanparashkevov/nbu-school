package org.example.gradingcenter.web.view.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeViewModel {

    private Long id;

    private String firstName;

    private String lastName;

    private Long schoolId;

    private String schoolName;

}
