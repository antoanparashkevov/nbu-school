package org.example.gradingcenter.data.repository;

import org.example.gradingcenter.data.entity.users.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
