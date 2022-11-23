package com.nteport.admin.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final String PATTERA_TIME = "yyyy-MM-dd HH:mm:ss";

    public static final String PATTERA_DATE = "yyyy-MM-dd";


    public static String getNowTime(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(PATTERA_TIME);
        return dtf.format(LocalDateTime.now());
    }

    public static String getNowDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(PATTERA_DATE);
        return dtf.format(LocalDate.now());
    }

}
