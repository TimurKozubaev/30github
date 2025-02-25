package com.kozubaev.ayu.osago.project.exeption;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    private int statusCode;
    private String error;
    private String message;

    public ErrorResponse(int statusCode, String error, String message) {
        this.statusCode = statusCode;
        this.error = error;
        this.message = message;
    }
}
