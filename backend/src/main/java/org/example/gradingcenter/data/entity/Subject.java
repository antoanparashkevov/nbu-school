package org.example.gradingcenter.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.gradingcenter.data.entity.enums.SubjectName;
import org.example.gradingcenter.data.entity.users.Teacher;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Subject extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubjectName name;

    @ManyToOne(optional = false)
    private Teacher teacher;

    @ManyToOne(optional = false)
    private Grade grade;

}
