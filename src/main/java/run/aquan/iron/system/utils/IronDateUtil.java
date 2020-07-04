package run.aquan.iron.system.utils;

import java.time.*;
import java.util.Date;

/**
 * @Class IronDateUtil
 * @Description TODO 时间处理工具
 * @Author Aquan
 * @Date 2019/12/26 13:24
 * @Version 1.0
 **/
public class IronDateUtil {

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static Date asDate(Long timestamp) {
        LocalDateTime localDateTime =LocalDateTime.ofEpochSecond(timestamp/1000,0, ZoneOffset.ofHours(8));
        Date date = asDate(localDateTime);
        return date;
    }

    public static Boolean checkDate(Date date, Date anotherDate) {
        if (date.compareTo(anotherDate)  == 0) {
            return true;
        } else {
            return false;
        }
    }

}
