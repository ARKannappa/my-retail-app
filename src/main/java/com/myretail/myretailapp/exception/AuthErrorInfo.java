/**
 * Error codes to handle Authentication exceptions
 */
package com.myretail.myretailapp.exception;

public enum AuthErrorInfo {

    OAUTH2_INVALID_REQUEST("invalid_request", "Unable to authenticate (OAuth)"),
    INSUFFICIENT_PRIVILEGE_ERROR("auth_insufficient_privilege", "Required security privilege missing."),
    MISSING_CREDENTIALS_ERROR("auth_missing_credentials", "Unable to authenticate due to missing credentials"),
    BAD_CREDENTIALS_SYNTAX_ERROR("auth_bad_credentials_syntax", "Unable to authenticate due to improperly constructed credentials"),
    BLANK_USERNAME_OR_PASSWORD_ERROR("auth_blank_username_or_password", "Unable to authenticate due to username or password being empty");

    private final String type;
    private final String message;

    AuthErrorInfo(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
