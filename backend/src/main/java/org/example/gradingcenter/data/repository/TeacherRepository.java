package org.example.gradingcenter.data.repository;

import org.example.gradingcenter.data.entity.users.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface TeacherRepository extends JpaSpecificationExecutor<Teacher>, JpaRepository<Teacher, Long> {

    Optional<Teacher> findByIdAndSchool_Id(Long id, Long schoolId);

}
