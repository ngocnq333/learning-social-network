package com.solution.ntq.common.validator;

import java.util.List;

public class ListUtils {
    public ListUtils() {}
    public static boolean checkListNotNull(List list){
        return (list != null && !list.isEmpty());
    }
}
