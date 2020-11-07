package com.doan.cnpm.service.dto;

import org.springframework.stereotype.Service;

@Service
public class PushNotificationDTO {

    private String notification;

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }
}
