package org.example.gradingcenter.data.repository.specification;

import jakarta.persistence.criteria.Predicate;
import org.example.gradingcenter.data.entity.School;
import org.example.gradingcenter.util.DataUtil;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SchoolSpecification {

    public static Specification<School> filterSchools(String name, String address, Long headmasterId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (DataUtil.isNotEmpty(name)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (DataUtil.isNotEmpty(address)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("address")), "%" + address.toLowerCase() + "%"));
            }
            if (headmasterId != null) {
                predicates.add(criteriaBuilder.equal(root.get("headmaster").get("id"), headmasterId));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
