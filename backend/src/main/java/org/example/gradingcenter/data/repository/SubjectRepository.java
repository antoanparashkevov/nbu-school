package org.example.gradingcenter.data.repository;

import org.example.gradingcenter.data.entity.Subject;
import org.example.gradingcenter.data.entity.enums.SubjectName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    boolean existsByNameAndGrade_NameAndSchool_IdAndTeacher_Id(SubjectName name, String gradeName, long schoolId, long teacherId);

    Optional<Subject> findByNameAndGrade_NameAndSchool_IdAndTeacher_Id(SubjectName name, String gradeName, Long schoolId, Long teacherId);

    List<Subject> findAllByGrade_NameAndSchool_Id(String gradeName, Long schoolId);

    List<Subject> findAllByTeacher_Id(Long teacherId);

}
