package com.solution.ntq.common.utils;

import com.solution.ntq.common.constant.Constant;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TimeUtils {
    public static long totalTime(int minute){
        return (minute * Constant.ONE_MINUTE);
    }
}
