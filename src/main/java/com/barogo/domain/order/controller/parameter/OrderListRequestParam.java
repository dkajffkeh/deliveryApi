package com.barogo.domain.order.controller.parameter;

import com.barogo.common.exception.ApiServerException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.barogo.common.code.ResultCode.RESULT_4000;
import static com.barogo.common.utils.DateTimeUtils.convertStringToLocalDate;
import static java.time.Period.between;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderListRequestParam {

    // format : yyyyMMdd
    @NotEmpty
    private String searchFrom;

    // format : yyyyMMdd
    @NotEmpty
    private String searchTo;

    // user Sequence number;
    @NotNull
    private Long userSeq;

    private static final int MAX_SEARCHABLE_DATE = 2;
    private static final String ACROSS_MAX_SEARCH_DATE_MSG = "주문조회 가능 기간은 검색시작일 포함 최대 3일입니다.";

    public LocalDateTime convertedFromTimeGet() {
        return convertStringToLocalDate(this.searchFrom).atTime(0,0);
    }

    public LocalDateTime convertedToTimeGet() {
        return convertStringToLocalDate(this.searchTo).plusDays(1).atTime(0,0, 0);
    }

    public LocalDate convertToDateGet(String date) {
        return convertStringToLocalDate(date);
    }

    public void validateRequestedDate() {
        int dateGap = between(
                convertToDateGet(this.searchFrom)
                ,convertToDateGet(this.searchTo)
        ).getDays();

        if(isAcrossMaxSearchDate(dateGap)) throw new ApiServerException(RESULT_4000,ACROSS_MAX_SEARCH_DATE_MSG);
    }

    public boolean isAcrossMaxSearchDate(int dateGap) {
        return dateGap > MAX_SEARCHABLE_DATE;
    }

}
