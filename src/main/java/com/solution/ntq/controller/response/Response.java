package com.solution.ntq.controller.response;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
public class Response<T> {
    int codeStatus;
    T data;
    String message;
    public Response(int codeStatus){
        this.codeStatus = codeStatus;
    }
    public Response(int codeStatus, T data) {
        this.codeStatus = codeStatus;
        this.data = data;
    }
}
