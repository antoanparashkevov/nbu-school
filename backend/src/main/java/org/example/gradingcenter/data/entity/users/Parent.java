package org.example.gradingcenter.data.entity.users;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Parent extends User {

    @ManyToMany
    @JoinTable(
            name = "parents_children",
            joinColumns = @JoinColumn(name = "parent_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "student_id", nullable = false)
    )
    private List<Student> children;

}
