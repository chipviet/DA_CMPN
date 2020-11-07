package com.doan.cnpm.controller;

import com.doan.cnpm.service.PushNotificationService;
import com.doan.cnpm.service.dto.CourseDTO;
import com.doan.cnpm.service.dto.PushNotificationDTO;
import com.doan.cnpm.service.response.SubjectDetailsResp;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/edu")
@Transactional
public class PushNotificationController {

    private final PushNotificationService pushNotificationService;

    public PushNotificationController(PushNotificationService pushNotificationService) {
        this.pushNotificationService = pushNotificationService;
    }

    @PostMapping(value="/v1/push-notification")
    public String pushNotification(HttpServletRequest request, @RequestBody PushNotificationDTO pushNotificationDTO){
        String success = "Successfully";
        try{
            PushNotificationService.sendMessageToAllUsers("Hello minh la Chip Viet");
            return success ;
        } catch (Exception e) {
            throw e;
        }


    }
}
