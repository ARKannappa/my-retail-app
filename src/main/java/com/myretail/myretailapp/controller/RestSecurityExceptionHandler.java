/*
* Exception handler for Authentication related exceptions
* */
package com.myretail.myretailapp.controller;

import com.myretail.myretailapp.dto.ApiResponse;
import com.myretail.myretailapp.exception.AppSecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Slf4j
@ControllerAdvice
public class RestSecurityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {AppSecurityException.class})
    @ResponseBody
    protected ResponseEntity<ApiResponse> handleSecurityException(AppSecurityException ex) {

        log.debug("SecurityException thrown.", ex);

        ApiResponse response = new ApiResponse(Integer.toString(UNAUTHORIZED.value()));

        response.addError(ex.getType(), ex.getMessage());

        if (!ex.getProperties().isEmpty()) {
            ex.getProperties().entrySet().stream().forEach(entry -> response.setProperty(entry.getKey(), entry.getValue()));
        }

        return ResponseEntity.status(UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(response);
    }


}
