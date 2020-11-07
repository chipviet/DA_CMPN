package com.doan.cnpm.service.exception;

public class AccessDeniedException extends  RuntimeException{

    private static final long serialVersionUID = 1L;

    public AccessDeniedException()  {
        super("Your account does not have access to this resource");
    }
}
