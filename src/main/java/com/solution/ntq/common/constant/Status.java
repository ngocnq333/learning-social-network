package com.solution.ntq.common.constant;



public enum Status {
    JOINED(1, "JOINED"),
    APPROVE(2, "WAITING_FOR_APPROVE"),
    NOTJOIN(3, "NOTJOIN"),
    UNKNOWN(4,"UNKNOWN");


    final int code;
    final String value;

    Status(int code, String value) {
        this.code = code;
        this.value = value;
    }


    public String value() {
        return this.value;
    }
}
