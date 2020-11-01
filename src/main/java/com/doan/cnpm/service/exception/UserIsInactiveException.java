package com.doan.cnpm.service.exception;

public class UserIsInactiveException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public UserIsInactiveException() {
        super("This account is deactivated, please contact our support team!!");
    }

}
