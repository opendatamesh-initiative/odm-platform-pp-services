package org.opendatamesh.platform.up.policy.api.v1.resources;

import lombok.Data;
import org.opendatamesh.platform.up.policy.api.v1.errors.PolicyserviceOpaAPIStandardError;

import java.util.Date;

@Data
public class ErrorResource {

    // HTTP Status code
    int status;

    // Standard error code
    String code;

    // Standard error description
    String description;

    // Exception message.
    // Do not include exception cause's message.
    // It is appended only to the log error message.
    String message;

    // Service endpoint
    String path;

    // Error timestamp
    Long timestamp;

    public ErrorResource() {
        this.timestamp = new Date().getTime();
    }

    public ErrorResource(int status, PolicyserviceOpaAPIStandardError error, String message, String path) {
        super();
        this.status = status;
        this.code = error.code();
        this.description = error.description();
        this.message = message;
        this.path = path;
    }

    public ErrorResource(int status, String errorCode, String errorDescription, String message, String path) {
        super();
        this.status = status;
        this.code = errorCode;
        this.description = errorDescription;
        this.message = message;
        this.path = path;
    }
}