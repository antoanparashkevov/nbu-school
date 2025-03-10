package org.example.gradingcenter.data.entity.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.gradingcenter.data.entity.Grade;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Student extends User {

    private int absences;

    @ManyToMany(mappedBy = "children")
    private List<Parent> parents;

    @ManyToOne(optional = false)
    private Grade grade;
}
