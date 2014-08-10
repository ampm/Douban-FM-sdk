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
    static Period period;
    public static final PeriodFormatter MMss_PERIOD_FORMATTER = new PeriodFormatterBuilder()
            .printZeroAlways()
            .minimumPrintedDigits(2)
            .appendMinutes()
            .appendSeparator(":")
            .printZeroAlways()
            .minimumPrintedDigits(2)
            .appendSeconds()
            .toFormatter();
    public static final PeriodFormatter HHMMss_PERIOD_FORMATTER = new PeriodFormatterBuilder()
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

    public static String periodToHHMMss(int duration){
        period = new Period();
        return HHMMss_PERIOD_FORMATTER.print(period.withMinutes(duration));
    }
    public static String periodToMMss(int duration){
        period = new Period();
        return MMss_PERIOD_FORMATTER.print(period.withMinutes(duration));
    }
}
