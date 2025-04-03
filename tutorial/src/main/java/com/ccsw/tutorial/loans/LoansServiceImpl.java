package com.ccsw.tutorial.loans;

import com.ccsw.tutorial.client.ClientService;
import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.loans.model.Loans;
import com.ccsw.tutorial.loans.model.LoansDto;
import com.ccsw.tutorial.loans.model.LoansSearchDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@Transactional
public class LoansServiceImpl implements LoansSerice {
    private final Long MAX_DAYS = 14l;
    private final Long MAX_GAMES_LOAN = 2l;

    @Autowired
    LoansRepository loansRepository;

    @Autowired
    GameService gameService;

    @Autowired
    ClientService clientService;

    @Override
    public Page<Loans> findPage(LoansSearchDto dto, Long clientId, Long gameId, LocalDate dayDate) {

        LoansSpecification clientSpec = new LoansSpecification(new SearchCriteria("client.id", ":", clientId));
        LoansSpecification gameSpec = new LoansSpecification(new SearchCriteria("game.id", ":", gameId));
        LoansSpecification fechaIniSpec = new LoansSpecification(new SearchCriteria("fechaIni","<=", dayDate));
        LoansSpecification fechaFinSpec = new LoansSpecification(new SearchCriteria("fechaFin",">=", dayDate));

        Specification<Loans> spec = Specification.where(clientSpec).and(gameSpec).and(fechaIniSpec).and(fechaFinSpec);

        return this.loansRepository.findAll(spec, dto.getPageable().getPageable());
    }

    @Override
    public Loans get(Long id) {
        return this.loansRepository.findById(id).orElse(null);
    }

    /**
     * TODO:
     * 1. Que la fecha de incio no sea posterior a la fin
     * 2. Max días por prestamo: 14
     * 3. 1 juego no puede estar en dos clientes el mismo día
     * 4. 1 cliente max 2 juegos por día
     * @param dto
     */
    @Override
    public void save(LoansDto dto) throws Exception {
        Loans loan = new Loans();

        loan.setClient(this.clientService.get(dto.getClient().getId()));
        loan.setGame(this.gameService.get(dto.getGame().getId()));

        checkDates(dto.getFechaIni(), dto.getFechaFin());
        checkClientGames(dto.getClient().getId(), dto.getFechaIni(), dto.getFechaFin());
        checkGameBorrowed(dto.getGame().getId(), dto.getFechaIni(), dto.getFechaFin());

        BeanUtils.copyProperties(dto,loan, "id");
        this.loansRepository.save(loan);
    }

    @Override
    public void delete(Long id) throws Exception {
        if (this.get(id) == null) {
            throw new Exception("Not exists.");
        }

        this.loansRepository.deleteById(id);
    }

    public void checkClientGames(Long clientId, LocalDate fechaIni, LocalDate fechaFin) throws Exception {
        Specification<Loans> clientLoansOnDate =
                new LoansSpecification(new SearchCriteria("client.id", ":", clientId))
                        .and(new LoansSpecification(new SearchCriteria("fechaIni", "<=", fechaFin)))
                        .and(new LoansSpecification(new SearchCriteria("fechaFin", ">=", fechaIni)));

        long gamecount = this.loansRepository.count(clientLoansOnDate);
        if ( gamecount >= MAX_GAMES_LOAN) {
            throw new Exception("No se puede alquilar, el cliente excede la cantidad de juegos permitidos.");
        }
    }

    public void checkGameBorrowed(Long gameId, LocalDate fechaIni, LocalDate fechaFin) throws Exception {
        Specification<Loans> clientLoansOnDate =
                new LoansSpecification(new SearchCriteria("game.id", ":", gameId))
                        .and(new LoansSpecification(new SearchCriteria("fechaIni", "<=", fechaFin)))
                        .and(new LoansSpecification(new SearchCriteria("fechaFin", ">=", fechaIni)));
        
        long isBorrowed= this.loansRepository.count(clientLoansOnDate);

        if (isBorrowed >= 1) {
           throw new Exception("Este juego ya se encuentra en alquier.");
        }
    }

    private void checkDates(LocalDate fechaIni, LocalDate fechaFin) throws Exception{
        if (fechaIni.isAfter(fechaFin)) {
            throw new Exception("La fecha de finalización no pude ser anterioir a la fehca de inicio del aqluiler.");
        }
        long maxDays = fechaIni.until(fechaFin, ChronoUnit.DAYS);
        if (maxDays > MAX_DAYS){
           throw new Exception("La duración del aquiler excede los 14 días permitidos.");
        }
    }
}
