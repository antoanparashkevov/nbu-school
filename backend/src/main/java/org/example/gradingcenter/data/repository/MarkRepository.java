package org.example.gradingcenter.data.repository;

import java.util.List;

import org.example.gradingcenter.data.entity.Mark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarkRepository extends JpaRepository<Mark, Long> {

    List<Mark> findAllByStudent_Id(Long studentId);

}
