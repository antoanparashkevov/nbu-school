package org.example.gradingcenter.data.repository;

import org.example.gradingcenter.data.entity.users.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findByIdAndSchool_Id(Long id, Long schoolId);

}
