package com.epam.esm.exception;

public class InvalidNameOfCertificate extends GiftCertificateException{


    public InvalidNameOfCertificate() {
    }

    public InvalidNameOfCertificate(String message, String errorCode) {
        super(message, errorCode);
    }

    public InvalidNameOfCertificate(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidNameOfCertificate(Throwable cause) {
        super(cause);
    }

}
