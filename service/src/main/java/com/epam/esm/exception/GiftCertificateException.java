package com.epam.esm.exception;

public class GiftCertificateException extends RuntimeException{
    private String errorCode;

    public GiftCertificateException() {
    }

    public GiftCertificateException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public GiftCertificateException(String message, Throwable cause) {
        super(message, cause);
    }

    public GiftCertificateException(Throwable cause) {
        super(cause);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
