package org.example.gradingcenter.data.repository;

import org.example.gradingcenter.data.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramRepository extends JpaRepository<Program, Long> {
}
