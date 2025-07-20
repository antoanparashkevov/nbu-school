package org.example.gradingcenter.data.repository.specification;

import jakarta.persistence.criteria.Predicate;
import org.example.gradingcenter.data.entity.ProgramSlot;
import org.example.gradingcenter.util.DataUtil;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProgramSpecification {

    public static Specification<ProgramSlot> filterProgram(String gradeName, Long schoolId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (DataUtil.isNotEmpty(gradeName)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("subject").get("grade").get("name")), "%" + gradeName.toLowerCase() + "%"));
            }
            if (schoolId != null) {
                predicates.add(criteriaBuilder.equal(root.get("subject").get("grade").get("school").get("id"), schoolId));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
