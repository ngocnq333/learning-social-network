package com.solution.ntq.common.utils;

import com.solution.ntq.common.constant.Constant;

public class TimeUtils {
    private TimeUtils(){}
    public static long totalTime(int minute){
        return (minute * Constant.ONE_MINUTE);
    }
}
