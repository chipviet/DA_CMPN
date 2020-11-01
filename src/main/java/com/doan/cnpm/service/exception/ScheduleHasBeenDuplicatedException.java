package com.doan.cnpm.service.exception;

public class ScheduleHasBeenDuplicatedException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public ScheduleHasBeenDuplicatedException() {
        super("This schedule has been duplicated, please try another schedule!!");
    }

}
