package org.example.gradingcenter.data.repository;

import org.example.gradingcenter.data.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
