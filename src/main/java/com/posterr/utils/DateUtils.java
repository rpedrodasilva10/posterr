package com.posterr.utils;

import com.posterr.exception.BusinessException;
import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class DateUtils {
    @SneakyThrows
    protected DateUtils() {
        throw new IllegalAccessException("Utility class!");
    }

    public static LocalDateTime treatStartDate(String startDate) throws BusinessException {
        try {
            return LocalDate.parse(Strings.isNotBlank(startDate) ? startDate : "1990-01-01").atStartOfDay();
        } catch (DateTimeParseException dateTimeParseException) {
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(), "Invalid date format!", "When informed, date parameters must be valid dates (YYYY-mm-dd)");
        }
    }

    public static LocalDateTime treatEndDate(String endDate) throws BusinessException {
        try {
            return LocalDate.parse(Strings.isNotBlank(endDate) ? endDate : LocalDate.now().toString()).atTime(LocalTime.MAX);
        } catch (DateTimeParseException dateTimeParseException) {
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(), "Invalid date format!", "When informed, date parameters must be valid dates (YYYY-mm-dd)");
        }
    }
}
