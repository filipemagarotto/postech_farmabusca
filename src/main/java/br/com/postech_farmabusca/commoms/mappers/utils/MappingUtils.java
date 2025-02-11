package br.com.postech_farmabusca.commoms.mappers.utils;

import java.time.LocalDateTime;

public class MappingUtils {
    public static final String LOCAL_DATE_TIME_NOW =
            "java(br.com.postech_farmabusca.commoms.mappers.utils.MappingUtils.localDateTimeNow())";

    public static LocalDateTime localDateTimeNow() {
        return LocalDateTime.now();
    }
}
