package org.example.gradingcenter.data.entity.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.gradingcenter.data.entity.Grade;
import org.example.gradingcenter.data.entity.School;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Student extends User {

    @Column(nullable = false)
    private String egn;

    @Column(columnDefinition = "INT DEFAULT 0")
    private int absences;

    @ManyToMany
    private List<Parent> parents;

    @ManyToOne
    private Grade grade;

    @ManyToOne(optional = false)
    private School school;

}
