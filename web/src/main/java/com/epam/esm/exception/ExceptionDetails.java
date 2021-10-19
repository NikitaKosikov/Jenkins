package com.epam.esm.exception;

public class ExceptionDetails {

    private final int httpStatus;
    private final String errorMessage;
    private final String errorCode;

    public ExceptionDetails(String errorMessage, String errorCode, int httpStatus) {
        this.httpStatus=httpStatus;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public int getHttpStatus() {
        return httpStatus;
    }
}
