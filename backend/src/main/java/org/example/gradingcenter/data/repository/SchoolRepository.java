package org.example.gradingcenter.data.repository;

import org.example.gradingcenter.data.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School, Long> {

    Optional<School> findByName(String name);

    Optional<School> findByHeadMasterId(Long id);

}
