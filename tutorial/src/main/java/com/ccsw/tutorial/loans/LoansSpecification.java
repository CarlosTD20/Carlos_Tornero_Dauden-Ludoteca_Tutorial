package com.ccsw.tutorial.loans;

import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.loans.model.Loans;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class LoansSpecification implements Specification<Loans> {

    private static final long serialVersionUID = 1L;

    private final SearchCriteria criteria;

    public LoansSpecification(SearchCriteria criteria) {

        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Loans> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Path<?> path = getPath(root);
        if (criteria.getOperation().equalsIgnoreCase(":") && criteria.getValue() != null) {
            if (path.getJavaType() == String.class) {
                return builder.like(path.as(String.class), "%" + criteria.getValue() + "%");
            } else {
                return builder.equal(path, criteria.getValue());
            }
        }

        if (path.getJavaType() == LocalDate.class) {
            if (criteria.getOperation().equalsIgnoreCase(">=") && criteria.getValue() != null) {
                return builder.greaterThanOrEqualTo(path.as(LocalDate.class), (LocalDate) criteria.getValue());
            }

            if (criteria.getOperation().equalsIgnoreCase("<=") && criteria.getValue() != null) {
                return builder.lessThanOrEqualTo(path.as(LocalDate.class), (LocalDate) criteria.getValue());
            }
        }
        return null;
    }

    private Path<String> getPath(Root<Loans> root) {
        String key = criteria.getKey();
        String[] split = key.split("[.]", 0);

        Path<String> expression = root.get(split[0]);
        for (int i = 1; i < split.length; i++) {
            expression = expression.get(split[i]);
        }

        return expression;
    }

}
