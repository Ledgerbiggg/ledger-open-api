package com.ledger.api_common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeUtil {

    public static String formatLocalDateTime(LocalDateTime localDateTime){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(dateTimeFormatter);
    }
    public static String getNowTimeFormat(){
        LocalDateTime localDateTime = LocalDateTime.now();
        return formatLocalDateTime(localDateTime);
    }

}
