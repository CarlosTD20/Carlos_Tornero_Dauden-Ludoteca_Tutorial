package com.ccsw.tutorial.loans;

import com.ccsw.tutorial.loans.model.Loans;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface LoansRepository extends CrudRepository<Loans, Long>, JpaSpecificationExecutor<Loans> {

    @Override
//    @EntityGraph(attributePaths = { "game", "client" })
    Page<Loans> findAll(Specification<Loans> spec, Pageable pageable);

    Loans findByGameId(Specification<Loans> spec);
}
