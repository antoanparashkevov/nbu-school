package org.example.gradingcenter.data.repository;

import org.example.gradingcenter.data.entity.Headmaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeadmasterRepository extends JpaRepository<Headmaster, Long> {

    Headmaster findByUsername(String username);

}
