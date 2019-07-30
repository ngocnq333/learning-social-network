package com.solution.ntq.service.validator;

public class StringUtils {
    private StringUtils(){}
    public static boolean isNullOrEmpty(String s)
    {
        return (s.isEmpty() || s==null);
    }

}
