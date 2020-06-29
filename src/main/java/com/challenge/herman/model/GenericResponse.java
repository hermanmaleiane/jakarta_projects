package com.challenge.herman.model;

import java.io.Serializable;
import java.util.Objects;

public class GenericResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;
    private String message;
    private String description;

    public GenericResponse() {
    }

    public GenericResponse(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenericResponse that = (GenericResponse) o;
        return code == that.code &&
                Objects.equals(message, that.message) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message, description);
    }

    @Override
    public String toString() {
        return "GenericResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}