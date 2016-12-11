package com.michael.http;

/**
 * Created by huangyanzhen on 2016/12/11.
 */

public class AppException extends Exception{
    public int statusCode;
    public String responseMessage;
    public enum ErrorType {TIMEOUT, SERVER, JSON, IO, FILE_NOT_FOUND, MANUAL}
    public ErrorType errorType;

    public AppException(int statusCode, String responseMessage) {
        super(responseMessage);
        this.errorType = ErrorType.SERVER;
        this.statusCode = statusCode;
        this.responseMessage = responseMessage;
    }

    public AppException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }
}
