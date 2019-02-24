package com.training.bookmanagement.util.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseModel {
    private int code;
	private String message;
	private Map<String, String> errors;

    public ResponseModel(int code, String message) {
        this.code = code;
        this.message = message;
        this.errors = new HashMap<>();
    }

    public ResponseModel(int code, String message, Map<String, String> errors) {
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    public void addError(String field, String errorMessage) {
        errors.put(field, errorMessage);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}