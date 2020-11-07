package com.doan.cnpm.service.exception;

public class TutorAlreadyCreatedException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public TutorAlreadyCreatedException() {
        super("This Tutor has been created!!");
    }
}
