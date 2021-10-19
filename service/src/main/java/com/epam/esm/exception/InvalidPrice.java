package com.epam.esm.exception;

public class InvalidPrice extends GiftCertificateException{


    public InvalidPrice() {
    }

    public InvalidPrice(String message, String errorCode) {
        super(message, errorCode);
    }

    public InvalidPrice(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPrice(Throwable cause) {
        super(cause);
    }
}
