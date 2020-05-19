package com.fruitcrops07.gateway.dto.response;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonPropertyOrder({
    "status",
    "data",
    "message"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ResponseMessage<T> {

    private T data;
    
    private Integer status;
    
    private String message;
    
    public ResponseMessage(T data, HttpStatus status, String message) {
        this.data = data;
        this.status = status.value();
        this.message = message;
    }
}
