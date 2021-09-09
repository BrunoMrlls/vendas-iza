package br.com.iza.service;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpStatusCodeException;

@ResponseStatus(BAD_REQUEST)
public class ResourceNotFoundException extends HttpStatusCodeException {

    public ResourceNotFoundException() {
        super(BAD_REQUEST);
    }

    public ResourceNotFoundException(String message) {
        super(BAD_REQUEST, message);
    }
}
