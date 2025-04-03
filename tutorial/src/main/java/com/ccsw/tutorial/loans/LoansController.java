package com.ccsw.tutorial.loans;

import com.ccsw.tutorial.loans.model.Loans;
import com.ccsw.tutorial.loans.model.LoansDto;
import com.ccsw.tutorial.loans.model.LoansSearchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name = "Loans", description = "API of Loan")
@RequestMapping(value = "/loan")
@RestController
@CrossOrigin(origins = "*")
public class LoansController {

    @Autowired
    LoansSerice loansService;

    @Autowired
    ModelMapper mapper;

    @Operation(summary = "Find", description = "Method that return a filtered list of Loans")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public Page<LoansDto> findPage(
            @RequestParam(value = "clientId", required = false) Long clientId,
            @RequestParam(value = "gameId", required = false) Long gameId,
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestBody LoansSearchDto dto
    ) {
        Page<Loans> page = this.loansService.findPage(dto,clientId, gameId, date);
        return new PageImpl<>(page.getContent().stream().map(e -> mapper.map(e, LoansDto.class)).collect(Collectors.toList()), page.getPageable(), page.getTotalElements());
    }

    @Operation(summary = "Save", description = "Method that saves a Loan")
    @RequestMapping(path = "", method = RequestMethod.PUT)
    public ResponseEntity<Map<String, String>>  save(@RequestBody LoansDto dto) {
        try {
            this.loansService.save(dto);
            return ResponseEntity.ok(Collections.singletonMap("message", "Loan saved successfully"));
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        }
    }

    @Operation(summary = "Delete", description = "Method that deletes a Loan")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) throws Exception{
        this.loansService.delete(id);
    }
}
