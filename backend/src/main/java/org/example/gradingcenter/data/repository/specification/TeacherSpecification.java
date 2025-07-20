package org.example.gradingcenter.data.repository.specification;

import jakarta.persistence.criteria.Predicate;
import org.example.gradingcenter.data.entity.users.Teacher;
import org.example.gradingcenter.util.DataUtil;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TeacherSpecification {

    public static Specification<Teacher> filterTeachers(String firstName, String lastName, Long schoolId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (DataUtil.isNotEmpty(firstName)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%"));
            }
            if (DataUtil.isNotEmpty(lastName)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%"));
            }
            if (schoolId != null) {
                predicates.add(criteriaBuilder.equal(root.get("school").get("id"), schoolId));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
