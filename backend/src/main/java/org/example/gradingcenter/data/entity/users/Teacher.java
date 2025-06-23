package org.example.gradingcenter.data.entity.users;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.gradingcenter.data.entity.School;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Teacher extends User implements Employee {

    @ManyToOne(optional = false)
    private School school;

}
