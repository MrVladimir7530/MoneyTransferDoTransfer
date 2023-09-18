package org.example.exceptions;

public class ProcessException extends Exception {
    private String code;
    private String description;
    private int status;

    public ProcessException() {
    }

    public ProcessException(String code, String description, int status) {
        this.code = code;
        this.description = description;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }
}
