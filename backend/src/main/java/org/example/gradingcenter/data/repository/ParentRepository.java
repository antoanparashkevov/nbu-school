package org.example.gradingcenter.data.repository;

import org.example.gradingcenter.data.entity.users.Parent;
import org.example.gradingcenter.data.entity.users.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ParentRepository extends JpaSpecificationExecutor<Parent>, JpaRepository<Parent, Long> {

    List<Parent> findAllByChildrenId(Long childId);

}
