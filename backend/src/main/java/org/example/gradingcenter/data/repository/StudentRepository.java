package org.example.gradingcenter.data.repository;

import org.example.gradingcenter.data.entity.users.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaSpecificationExecutor<Student>, JpaRepository<Student, Long> {

    List<Student> findAllByParents_Id(Long parentId);

    Optional<Student> findByEgn(String egn);
}
