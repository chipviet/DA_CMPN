package com.doan.cnpm.service.exception;

public class EmailAlreadyExistException  extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public EmailAlreadyExistException() {
        super("This email already exist, please use another email address!!");
    }

}
