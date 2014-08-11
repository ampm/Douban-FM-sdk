package com.zzxhdzj.app.base.utils;

import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 7/6/14
 * To change this template use File | Settings | File Templates.
 */
public class TimeUtil {
    private static DateTimeFormatter mmssFormatter = DateTimeFormat.forPattern("mm:ss");
    private static DateTimeFormatter HHmmssFormatter = DateTimeFormat.forPattern("HH:mm:ss");

    public static String periodToHHMMss(int duration){
        return HHmmssFormatter.print(duration);
    }
    public static String periodToMMss(int duration){
        return mmssFormatter.print(duration);
    }
}
