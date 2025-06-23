package org.example.gradingcenter.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.gradingcenter.data.entity.users.Student;
import org.example.gradingcenter.data.entity.users.Teacher;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Mark extends BaseEntity {

    @DecimalMin("2.00")
    @DecimalMax("6.00")
    @Column(nullable = false)
    private double mark;

    @ManyToOne(optional = false)
    private Subject subject;

    @ManyToOne(optional = false)
    private Student student;

    @ManyToOne(optional = false)
    private Teacher teacher;

}
