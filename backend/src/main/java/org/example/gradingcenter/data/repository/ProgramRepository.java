package org.example.gradingcenter.data.repository;

import org.example.gradingcenter.data.entity.ProgramSlot;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProgramRepository extends JpaRepository<ProgramSlot, Long>, JpaSpecificationExecutor<ProgramSlot> {

    List<ProgramSlot> findBySubject_School_IdAndSubject_Grade_NameOrderByDayOfWeekAscSubjectSequenceAsc(
            Long schoolId,
            String gradeName
    );
}
