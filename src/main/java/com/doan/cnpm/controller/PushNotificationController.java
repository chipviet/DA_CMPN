package com.doan.cnpm.controller;

import com.doan.cnpm.domain.Device;
import com.doan.cnpm.domain.User;
import com.doan.cnpm.repositories.DeviceRepository;
import com.doan.cnpm.repositories.UserRepository;
import com.doan.cnpm.service.PushNotificationService;
import com.doan.cnpm.service.dto.PushNotificationDTO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/edu")
@Transactional
public class PushNotificationController {

    private final PushNotificationService pushNotificationService;

    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;

    public PushNotificationController(PushNotificationService pushNotificationService, DeviceRepository deviceRepository, UserRepository userRepository) {
        this.pushNotificationService = pushNotificationService;
        this.deviceRepository = deviceRepository;
        this.userRepository = userRepository;
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

    @PostMapping(value="/v1/push-notification/specific-user")
    public String pushNotificationToSpecificUser(HttpServletRequest request, @RequestBody PushNotificationDTO pushNotificationDTO){
        String success = "Successfully";
        String username = request.getHeader("username");
        Optional<User> user = userRepository.findOneByUsername(username);
        List<Device> devices = deviceRepository.findByUser(user.get());
        try{
            PushNotificationService.sendMessageToUser("pushNotificationToSpecificUser",devices);
            return success ;
        } catch (Exception e) {
            throw e;
        }
    }
}
