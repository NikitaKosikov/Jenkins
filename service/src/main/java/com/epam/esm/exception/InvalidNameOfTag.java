package com.epam.esm.exception;

public class InvalidNameOfTag extends GiftCertificateException{

    public InvalidNameOfTag() {
    }

    public InvalidNameOfTag(String message, String errorCode) {
        super(message, errorCode);
    }

    public InvalidNameOfTag(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidNameOfTag(Throwable cause) {
        super(cause);
    }

}
