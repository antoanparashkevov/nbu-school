package org.example.gradingcenter.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchoolOutDto extends SchoolDto {

    private long id;

    private String headmasterFirstName;

    private String headmasterLastName;

}
