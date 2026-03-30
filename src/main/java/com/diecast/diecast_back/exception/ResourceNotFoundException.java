package com.diecast.diecast_back.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String mensagem) {
        super(mensagem);
    }
}