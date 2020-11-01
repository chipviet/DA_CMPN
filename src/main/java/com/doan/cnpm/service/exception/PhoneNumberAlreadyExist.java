package com.doan.cnpm.service.exception;

public class PhoneNumberAlreadyExist extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public PhoneNumberAlreadyExist() {
        super("This phone number already exists, please use another phone number!!");
    }

}