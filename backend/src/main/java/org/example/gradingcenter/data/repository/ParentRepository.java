package org.example.gradingcenter.data.repository;

import org.example.gradingcenter.data.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentRepository extends JpaRepository<Parent, Long> {
}
