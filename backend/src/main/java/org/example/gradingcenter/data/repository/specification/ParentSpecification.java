package org.example.gradingcenter.data.repository.specification;

import jakarta.persistence.criteria.Predicate;
import org.example.gradingcenter.data.entity.users.Parent;
import org.example.gradingcenter.util.DataUtil;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ParentSpecification {

    public static Specification<Parent> filterParents(String firstName, String lastName) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (DataUtil.isNotEmpty(firstName)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%"));
            }
            if (DataUtil.isNotEmpty(lastName)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
