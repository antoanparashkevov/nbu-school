package org.example.gradingcenter.data.repository;

import org.example.gradingcenter.data.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    Optional<Grade> findByNameAndSchool_Id(String name, Long schoolId);

    @Query("SELECT s.grade.name FROM Student s WHERE s.id = :studentId")
    String findGradeNameByStudentId(@Param("studentId") Long studentId);

}
