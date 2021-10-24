package com.epam.esm.exception;

public class InvalidDuration extends GiftCertificateException{
    public InvalidDuration() {
    }

    public InvalidDuration(String message, String errorCode) {
        super(message, errorCode);
    }

    public InvalidDuration(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDuration(Throwable cause) {
        super(cause);
    }
}
