package com.quadcore.quadcore.Entities;

import lombok.Getter;
import lombok.Setter;
import org.apache.coyote.Request;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class RequestToken {
    public String message;

    public HttpStatus status;
    public RequestToken(String message, HttpStatus status) {
        this.message = message;
        this.status = status;

    }
}
