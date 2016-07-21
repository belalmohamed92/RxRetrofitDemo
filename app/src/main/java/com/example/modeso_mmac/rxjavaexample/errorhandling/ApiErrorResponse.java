package com.example.modeso_mmac.rxjavaexample.errorhandling;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Belal Mohamed on 7/21/16.
 * www.modeso.ch
 */
public class ApiErrorResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
