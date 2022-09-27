package com.gimnasiolomas.ar.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.Map;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {
    private int status;
    private String message;
    private long timeStamp;
    private String path;
    private Map<String, String> validationErrors;

    public ApiError(){}

    public ApiError(int status, String message, String path){
        this.status = status;
        this.message = message;
        this.path = path;
        this.timeStamp = new Date().getTime();
    }
}
