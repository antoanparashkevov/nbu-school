package org.example.gradingcenter.data.repository;

import org.example.gradingcenter.data.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School, Long> {

    Optional<School> findByName(String name);

    Optional<School> findByHeadmasterId(Long id);

    @Query("SELECT s.school.id FROM Student s WHERE s.id = :studentId")
    Long findSchoolIdByStudentId(@Param("studentId") Long studentId);

}
