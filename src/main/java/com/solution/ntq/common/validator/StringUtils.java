package com.solution.ntq.common.validator;

public class StringUtils {
    private StringUtils(){}
    public static boolean isNullOrEmpty(String s)
    {
        return (s == null || s.isEmpty());
    }

}
