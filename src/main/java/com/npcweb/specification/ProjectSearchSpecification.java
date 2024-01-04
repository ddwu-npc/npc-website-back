package com.npcweb.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.npcweb.domain.Project;

public class ProjectSearchSpecification {
    public static Specification<Project> findBySearch(String process, String type, String pname) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (process != null && !process.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("process"), process));
            }
            if (type != null && !type.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("type"), type));
            }
            if (pname != null && !pname.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("pname")), "%" + pname.toLowerCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

