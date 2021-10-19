package com.epam.esm.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.security.InvalidParameterException;
import java.util.Locale;

@RestControllerAdvice
public class ExceptionController {

    private static final String PARAMETERS_INCORRECT_MESSAGE = "parameters.incorrect";

    private final MessageSource messageSource;

    @Autowired
    public ExceptionController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionDetails> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, Locale locale) {
        String errorMessage = messageSource.getMessage(PARAMETERS_INCORRECT_MESSAGE, new java.lang.String[]{}, locale);
        return new ResponseEntity<>(new ExceptionDetails(errorMessage, e.getErrorCode(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GiftCertificateException.class)
    public ResponseEntity<ExceptionDetails> invalidNameOfCertificate(GiftCertificateException e, Locale locale) {
        String errorMessage = messageSource.getMessage(e.getMessage(), new String[]{}, locale);
        return new ResponseEntity<>(new ExceptionDetails(errorMessage, e.getErrorCode(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidNameOfTag.class)
    public ResponseEntity<ExceptionDetails> invalidNameOfTag(InvalidNameOfTag e, Locale locale) {
        String errorMessage = messageSource.getMessage(e.getMessage(), new String[]{}, locale);
        return new ResponseEntity<>(new ExceptionDetails(errorMessage, e.getErrorCode(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ExceptionDetails> invalidParameter(InvalidNameOfTag e, Locale locale) {
        String errorMessage = messageSource.getMessage(e.getMessage(), new String[]{}, locale);
        return new ResponseEntity<>(new ExceptionDetails(errorMessage, e.getErrorCode(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }
}
