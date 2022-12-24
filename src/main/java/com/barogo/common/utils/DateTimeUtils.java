package com.barogo.common.utils;

import com.barogo.common.code.ResultCode;
import com.barogo.common.exception.ApiServerException;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Slf4j
public class DateTimeUtils {

    public static final String DATE_FORMAT = "yyyyMMdd";

    public static final String TIME_FORMAT_WITH_SECOND = "HHmmss";

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    public static final DateTimeFormatter DATE_FORMATTER_WITH_HOUR_AND_SECOND =
            DateTimeFormatter.ofPattern(DATE_FORMAT+TIME_FORMAT_WITH_SECOND);

    public static final String DATE_FORMAT_NOT_VALID_MSG = "요청 포멧이 유효하지 않습니다. [yyyyMMdd]";

    /**
     * @return yyyyMMdd format String
     */
    public static String convertLocalDateToString(LocalDate localDate) {
        return localDate.format(DATE_FORMATTER);
    }

    /**
     *
     * @param date yyyyMMdd format String
     * @return LocalDate
     */
    public static LocalDate convertStringToLocalDate(String date) {
        try {
            return LocalDate.parse(date, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            log.info("DataTime Format Exception : {}",date);
            throw new ApiServerException(ResultCode.RESULT_4000,DATE_FORMAT_NOT_VALID_MSG);
        }
    }

    public static String convertLocalDateTimeToString(LocalDateTime localDateTime) {
        return localDateTime.format(DATE_FORMATTER_WITH_HOUR_AND_SECOND);
    }
}
