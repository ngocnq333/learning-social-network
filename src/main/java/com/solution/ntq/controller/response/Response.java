package com.solution.ntq.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Response<T> {
    private int codeStatus;
    private T data;
    private String message;
    public Response(int codeStatus){
        this.codeStatus = codeStatus;
    }
    public Response(int codeStatus, T data) {
        this.codeStatus = codeStatus;
        this.data = data;
    }
}
