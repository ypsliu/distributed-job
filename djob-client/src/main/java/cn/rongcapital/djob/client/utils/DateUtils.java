package cn.rongcapital.djob.client.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lilupeng on 17/04/2017
 *
 */
public class DateUtils {

    private static DateFormat format1 = new SimpleDateFormat("yyyyMMddHHmmss"); // 20170120141108

    /**
     *
     * @return
     */
    public static String getDateString() {
        return getDateString(new Date());
    }

    /**
     *
     * @param date
     * @return
     */
    public static String getDateString(Date date) {
        return format1.format(date);
    }
}
