package com.solution.ntq.common.constant;

public enum Level {
    BEGINNER(1, "BEGINNER"),
    INTERMEDISE(2, "INTERMEDISE"),
    EXPERT(3, "EXPERT");

    final int code;
    final String value;

    Level(int code, String value) {
        this.code = code;
        this.value = value;
    }


    public String value() {
        return this.value;
    }
}

