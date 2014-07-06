package com.zzxhdzj.app.base.utils;

import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 7/6/14
 * To change this template use File | Settings | File Templates.
 */
public class TimeUtil {
    public static String periodToHHMMss(int duration){
        PeriodFormatter fmt = new PeriodFormatterBuilder()
                .printZeroAlways()
                .minimumPrintedDigits(2)
                .appendHours()
                .appendSeparator(":")
                .printZeroAlways()
                .minimumPrintedDigits(2)
                .appendMinutes()
                .appendSeparator(":")
                .printZeroAlways()
                .minimumPrintedDigits(2)
                .appendSeconds()
                .toFormatter();
        return fmt.print(new Period(duration));
    }
    public static String periodToMMss(int duration){
        PeriodFormatter fmt = new PeriodFormatterBuilder()
                .printZeroAlways()
                .minimumPrintedDigits(2)
                .appendMinutes()
                .appendSeparator(":")
                .printZeroAlways()
                .minimumPrintedDigits(2)
                .appendSeconds()
                .toFormatter();
        return fmt.print(new Period(duration));
    }
}
