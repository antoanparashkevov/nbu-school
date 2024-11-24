package org.example.gradingcenter.data.repository;

import org.example.gradingcenter.data.entity.Mark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarkRepository extends JpaRepository<Mark, Long> {
}
