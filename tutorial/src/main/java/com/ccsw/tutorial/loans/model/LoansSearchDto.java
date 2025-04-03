package com.ccsw.tutorial.loans.model;

import com.ccsw.tutorial.common.pagination.PageableRequest;

public class LoansSearchDto {
    private PageableRequest pageable;

    public PageableRequest getPageable() {
        return pageable;
    }

    public void setPageable(PageableRequest pageable) {
        this.pageable = pageable;
    }
}
