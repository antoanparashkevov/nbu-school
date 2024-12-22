package org.example.gradingcenter.data.repository;

import org.example.gradingcenter.data.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
