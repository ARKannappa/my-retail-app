package com.myretail.myretailapp.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * A runtime exception representing a failure while calling a downstream system.
 */
public class ExternalServiceClientException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String type = null;

    // general purpose map
    private Map<String, Object> properties = new HashMap<>();

    public ExternalServiceClientException(String type, String message) {
        super(message);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperty(String key, Object value) {
        properties.put(key, value);
    }
}
