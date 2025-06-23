package org.example.gradingcenter.data.repository;

import org.example.gradingcenter.data.entity.users.Headmaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HeadmasterRepository extends JpaSpecificationExecutor<Headmaster>, JpaRepository<Headmaster, Long> {
}
