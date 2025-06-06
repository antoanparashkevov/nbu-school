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
public class StudentSearchViewModel {

    private String firstName;

    private String lastName;

    private int absencesFrom;

    private int absencesTo;

    private String gradeName;

    private Long schoolId;

}
