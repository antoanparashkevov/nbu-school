package org.example.gradingcenter.data.repository.specification;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Predicate;
import org.example.gradingcenter.data.entity.users.Student;
import org.example.gradingcenter.util.DataUtil;
import org.springframework.data.jpa.domain.Specification;

public class StudentSpecification {

    public static Specification<Student> filterRecords(String egn, String firstName, String lastName, String gradeName,
                                                       Long schoolId, Integer absencesFrom, Integer absencesTo) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (DataUtil.isNotEmpty(egn)) {
                predicates.add(criteriaBuilder.equal(root.get("egn"), egn));
            }
            if (DataUtil.isNotEmpty(firstName)) {
                predicates.add(criteriaBuilder.like(root.get("firstName"), "%" + firstName + "%"));
            }
            if (DataUtil.isNotEmpty(lastName)) {
                predicates.add(criteriaBuilder.like(root.get("lastName"), "%" + lastName + "%"));
            }
            if (DataUtil.isNotEmpty(gradeName)) {
                predicates.add(criteriaBuilder.equal(root.get("grade").get("name"), gradeName));
            }
            if (schoolId != null) {
                predicates.add(criteriaBuilder.equal(root.get("school").get("id"), schoolId));
            }
            if (absencesFrom != null) {
                if (absencesTo != null) {
                    predicates.add(criteriaBuilder.between(root.get("absences"), absencesFrom, absencesTo));
                } else {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("absences"), absencesFrom));
                }
            }
            if (absencesTo != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("absences"), absencesTo));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
