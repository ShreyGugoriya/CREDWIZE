package com.quadcore.quadcore.Entities;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
@Getter
@Setter
public class ResponseHandler {
    public String message;
    public CardAllocation cardAllocation;
    public HttpStatus status;
    public ResponseHandler(String message, HttpStatus status,CardAllocation cardAllocation) {
        this.message = message;
        this.status = status;
        this.cardAllocation=cardAllocation;
    }

}