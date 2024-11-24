package org.example.gradingcenter.data.repository;

import org.example.gradingcenter.data.entity.Headmaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarkRepository extends JpaRepository<Headmaster, Long> {
}
