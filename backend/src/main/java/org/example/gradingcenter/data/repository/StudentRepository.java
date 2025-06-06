package org.example.gradingcenter.data.repository;

import org.example.gradingcenter.data.entity.users.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudentRepository extends JpaSpecificationExecutor<Student>, JpaRepository<Student, Long> {
}
