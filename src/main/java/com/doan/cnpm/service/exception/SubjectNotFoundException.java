package com.doan.cnpm.service.exception;

public class SubjectNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public SubjectNotFoundException() {
        super("Invalid subject Id!!");
    }
}
