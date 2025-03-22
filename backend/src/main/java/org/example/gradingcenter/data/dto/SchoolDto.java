package org.example.gradingcenter.data.dto;

import lombok.*;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SchoolDto {

    private String name;

    private String address;

    private long headmasterId;

}
