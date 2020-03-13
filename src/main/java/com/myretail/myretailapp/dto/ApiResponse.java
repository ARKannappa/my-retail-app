/*
* Base information for API response from application
* */
package com.myretail.myretailapp.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class ApiResponse {
    // HTTP status code
    private String status;

    // HTTP method that this response is being generated for
    private String action;

    // logical or unexpected error to be returned in response
    private List<ExceptionInfo> errors;

    // general purpose map that can be used in the response
    private Map<String, Object> properties;


    public ApiResponse() {
        this.status = "200";
        this.properties = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    public ApiResponse(String status) {
        this.status = status;
        this.properties = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    public void addError(String code, String description) {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        errors.add(new ExceptionInfo(code, description));
    }

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonAnyGetter
    public Map<String, Object> getProperties() {
        return properties;
    }

    @JsonAnySetter
    public void setProperty(String key, Object value) {
        properties.put(key, value);
    }

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public List<ExceptionInfo> getErrors() {
        return errors;
    }

    /**
     * Internal class to save logical/unexpected error responses.
     */
    public class ExceptionInfo {

        private String errorCode;
        private String errorDescription;

        public ExceptionInfo(String errorCode, String errorDescription)

        {
            this.errorCode = errorCode;
            this.errorDescription = errorDescription;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorDescription() {
            return errorDescription;
        }

        public void setErrorDescription(String errorDescription) {
            this.errorDescription = errorDescription;
        }

    }
}
