package org.example.gradingcenter.data.repository;

import org.example.gradingcenter.data.entity.Subject;
import org.example.gradingcenter.data.entity.enums.SubjectName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    boolean existsByNameAndGrade_NameAndSchool_Id(SubjectName name, String  gradeName, long school_id);

}
