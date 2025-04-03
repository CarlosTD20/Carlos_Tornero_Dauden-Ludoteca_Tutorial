package com.ccsw.tutorial.loans;

import com.ccsw.tutorial.loans.model.Loans;
import com.ccsw.tutorial.loans.model.LoansDto;
import com.ccsw.tutorial.loans.model.LoansSearchDto;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface LoansSerice {

    Page<Loans> findPage(LoansSearchDto dto, Long clientId, Long gameId, LocalDate dayDate);

    Loans get(Long id);

    void save(LoansDto dto) throws Exception;

    void delete(Long id) throws Exception;
}
