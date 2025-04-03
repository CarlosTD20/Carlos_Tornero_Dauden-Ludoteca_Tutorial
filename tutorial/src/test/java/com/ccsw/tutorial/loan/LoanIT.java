package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.common.pagination.PageableRequest;
import com.ccsw.tutorial.config.ResponsePage;
import com.ccsw.tutorial.loans.model.LoansDto;
import com.ccsw.tutorial.loans.model.LoansSearchDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LoanIT {

    public static final String LOCALHOST = "http://localhost:";
    public static final String SERVICE_PATH = "/loan";

    public static final Long EXISTS_LOAN_ID = 1L;
    public static final Long NOT_EXISTS_LOAN_ID = 30L;
    public static final Long EXISTS_CLIENT_ID = 1L;
    public static final Long NOT_EXISTS_CLIENT_ID= 30L;
    public static final Long EXISTS_GAME_ID = 1L;
    public static final Long NOT_EXISTS_GAME_ID = 30L;
    public static final String EXISTS_DATE = "2025-01-06";
    public static final String NOT_EXISTS_DATE = "2027-01-06";

    public static final String CLIENT_PARAM_ID = "clientId";
    public static final String GAME_PARAM_ID = "gameId";
    public static final String DATE_PARAM_ID = "date";

    public static final int TOTAL_LOANS = 5;
    public static final int PAGE_WITH_SMALL_SIZE = 2;
    public static final int PAGE_SIZE = 5;
    public static final int PAGE_WITHOUT_SIZE = 100;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    ParameterizedTypeReference<ResponsePage<LoansDto>> responseTypePage = new ParameterizedTypeReference<ResponsePage<LoansDto>>() {};
    ParameterizedTypeReference<List<LoansDto>> responseTypeList = new ParameterizedTypeReference<List<LoansDto>>() {};

    private String getUrlWithParams() {
        return UriComponentsBuilder.fromHttpUrl(LOCALHOST + port + SERVICE_PATH)
                .queryParam(CLIENT_PARAM_ID, "{" + CLIENT_PARAM_ID + "}")
                .queryParam(GAME_PARAM_ID, "{" + GAME_PARAM_ID + "}")
                .queryParam(DATE_PARAM_ID, "{" + DATE_PARAM_ID + "}")
                .encode()
                .toUriString();
    }

    @Test
    public void findWithoutFiltersAndWithoutPaginationShouldReturnAllLoansInDB() {

        Map<String, Object> params = new HashMap<>();
        params.put(CLIENT_PARAM_ID, null);
        params.put(GAME_PARAM_ID, null);
        params.put(DATE_PARAM_ID, null);

        LoansSearchDto searchDto = new LoansSearchDto();
        searchDto.setPageable(new PageableRequest(0,PAGE_WITHOUT_SIZE));

        ResponseEntity<ResponsePage<LoansDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage, params);

        assertNotNull(response);
        assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());
        assertEquals(TOTAL_LOANS, response.getBody().getContent().size());
    }

    @Test
    public void findWithClientFilterShouldReturnLoanWithSameClientIds() {
        Map<String, Object> params = new HashMap<>();
        params.put(CLIENT_PARAM_ID, EXISTS_CLIENT_ID);
        params.put(GAME_PARAM_ID, null);
        params.put(DATE_PARAM_ID, null);

        LoansSearchDto searchDto = new LoansSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_WITHOUT_SIZE));

        ResponseEntity<ResponsePage<LoansDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage, params);

        assertNotNull(response);
        assertEquals(2, response.getBody().getTotalElements());
        assertEquals(2, response.getBody().getContent().size());
    }

    @Test
    public void findWithGameFilterShouldReturnLoanWithSameGameIds() {
        Map<String, Object> params = new HashMap<>();
        params.put(CLIENT_PARAM_ID, null);
        params.put(GAME_PARAM_ID, EXISTS_GAME_ID);
        params.put(DATE_PARAM_ID, null);

        LoansSearchDto searchDto = new LoansSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_WITHOUT_SIZE));

        ResponseEntity<ResponsePage<LoansDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage, params);

        assertNotNull(response);
        assertEquals(1, response.getBody().getTotalElements());
        assertEquals(1, response.getBody().getContent().size());
    }

    @Test
    public void findWithDateFilterShouldReturnLoanWithSameDates() {
        Map<String, Object> params = new HashMap<>();
        params.put(CLIENT_PARAM_ID, null);
        params.put(GAME_PARAM_ID, null);
        params.put(DATE_PARAM_ID, EXISTS_DATE);

        LoansSearchDto searchDto = new LoansSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_WITHOUT_SIZE));

        ResponseEntity<ResponsePage<LoansDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage, params);

        assertNotNull(response);
        assertEquals(2, response.getBody().getTotalElements());
        assertEquals(2, response.getBody().getContent().size());
    }

    @Test
    public void findWithAllFiltersShouldReturnLoanWithAllFilters() {
        Map<String, Object> params = new HashMap<>();
        params.put(CLIENT_PARAM_ID, EXISTS_CLIENT_ID);
        params.put(GAME_PARAM_ID, EXISTS_CLIENT_ID);
        params.put(DATE_PARAM_ID, EXISTS_DATE);

        LoansSearchDto searchDto = new LoansSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_WITHOUT_SIZE));

        ResponseEntity<ResponsePage<LoansDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage, params);

        assertNotNull(response);
        assertEquals(1, response.getBody().getTotalElements());
        assertEquals(1, response.getBody().getContent().size());
    }

    @Test
    public void findWithPageSizeShouldReturnQuantityOfLoansOfPageSize() {
        Map<String, Object> params = new HashMap<>();
        params.put(CLIENT_PARAM_ID, null);
        params.put(GAME_PARAM_ID, null);
        params.put(DATE_PARAM_ID, null);

        LoansSearchDto searchDto = new LoansSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_WITH_SMALL_SIZE));

        ResponseEntity<ResponsePage<LoansDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage, params);

        assertNotNull(response);
        assertEquals(5, response.getBody().getTotalElements());
        assertEquals(2, response.getBody().getContent().size());
    }

    @Test
    public void DeleteWithExistIdShouldDelteLoan() {
        long newLoansSize = TOTAL_LOANS - 1;

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + EXISTS_LOAN_ID, HttpMethod.DELETE, null, Void.class);

        LoansSearchDto searchDto = new LoansSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));
        ResponseEntity<ResponsePage<LoansDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(newLoansSize, response.getBody().getContent().size());
    }
}
