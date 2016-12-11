package com.michael.http;

/**
 * Created by huangyanzhen on 2016/12/11.
 */

public class AppException extends Exception{
    public int statusCode;
    public String responseMessage;

    public AppException(int statusCode, String responseMessage) {
        super(responseMessage);
        this.statusCode = statusCode;
        this.responseMessage = responseMessage;
    }

    public AppException(String message) {
        super(message);
    }
}
