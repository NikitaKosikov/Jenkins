package com.epam.esm.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthExceptionHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Map<String, Object> exceptionDetails = new HashMap<>();
        exceptionDetails.put("timestamp", Calendar.getInstance().getTime());
        exceptionDetails.put("exception_message", exception.getMessage());
        exceptionDetails.put("error_code", 2525);

        response.getOutputStream()
                .println(objectMapper.writeValueAsString(exceptionDetails));
    }
}
