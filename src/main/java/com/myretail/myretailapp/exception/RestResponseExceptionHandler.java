/*
 * Exception handler to handle exceptions thrown back to controller
 * */
package com.myretail.myretailapp.exception;

import com.myretail.myretailapp.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;


@Slf4j
@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ExternalServiceClientException.class})
    @ResponseBody
    protected ResponseEntity<ApiResponse> handleExternalServiceClientException(ExternalServiceClientException exception) {


        ApiResponse response = new ApiResponse();

        response.setStatus(String.valueOf(INTERNAL_SERVER_ERROR.value()));
        response.addError(exception.getType(), "Internal service error. Couldn't fulfill this request." + exception.toString());

        return ResponseEntity.status(INTERNAL_SERVER_ERROR.value()).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @ExceptionHandler(value = {DataNotAvailableException.class})
    @ResponseBody
    protected ResponseEntity<ApiResponse> handleDataNotAvailableException(DataNotAvailableException exception) {


        ApiResponse response = new ApiResponse();

        response.setStatus(String.valueOf(NOT_FOUND.value()));
        response.addError(exception.getType(), "Data error. Couldn't fulfill this request." + exception.toString());

        return ResponseEntity.status(NOT_FOUND.value()).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @ExceptionHandler(value = {ExternalServiceServerException.class})
    @ResponseBody
    protected ResponseEntity<ApiResponse> handleExternalServiceServerException(ExternalServiceServerException exception) {


        ApiResponse response = new ApiResponse();

        response.setStatus(String.valueOf(SERVICE_UNAVAILABLE.value()));
        response.addError(exception.getType(), "Internal service error. Couldn't fulfill this request." + exception.toString());

        return ResponseEntity.status(SERVICE_UNAVAILABLE.value()).contentType(MediaType.APPLICATION_JSON).body(response);
    }


    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    protected ResponseEntity<ApiResponse> handleUnknownException(Exception ex) {
        log.error("Unknown exception happened", ex);

        ApiResponse response = new ApiResponse();

        response.setStatus(String.valueOf(INTERNAL_SERVER_ERROR.value()));
        response.addError(INTERNAL_SERVER_ERROR.name(), "Unknown error happened. Couldn't fulfill this request."  + ex.toString());

        return ResponseEntity.status(INTERNAL_SERVER_ERROR.value()).contentType(MediaType.APPLICATION_JSON).body(response);
    }

}