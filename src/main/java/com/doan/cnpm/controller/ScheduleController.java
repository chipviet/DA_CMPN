package com.doan.cnpm.controller;

import com.doan.cnpm.domain.Lesson;
import com.doan.cnpm.domain.Schedule;
import com.doan.cnpm.domain.User;
import com.doan.cnpm.repositories.ScheduleRepository;
import com.doan.cnpm.repositories.UserRepository;
import com.doan.cnpm.service.ScheduleService;
import com.doan.cnpm.service.UserAuthorityService;
import com.doan.cnpm.service.dto.ScheduleDTO;
import com.doan.cnpm.service.exception.AccessDeniedException;
import com.doan.cnpm.service.response.DayDetailResp;
import com.doan.cnpm.service.response.LessonDetailResp;
import com.doan.cnpm.service.response.LiteracyDetailResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

import static java.lang.Integer.parseInt;

@CrossOrigin(origins = "http://haimai.ddns.net:9090", maxAge = 3600)
@RestController
@RequestMapping("/api/edu")
@Transactional
public class ScheduleController {

    private final Logger log = LoggerFactory.getLogger(ScheduleController.class);

    private final ScheduleRepository scheduleRepository;

    private final UserRepository userRepository;

    private final UserAuthorityService userAuthorityService;

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleRepository scheduleRepository,
                              UserRepository userRepository,
                              UserAuthorityService userAuthorityService,
                              ScheduleService scheduleService) {
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
        this.userAuthorityService = userAuthorityService;
        this.scheduleService = scheduleService;
    }

    @GetMapping("v1/schedule")
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    @GetMapping("v1/schedule/details")
    public ResponseEntity<Schedule> getScheduleDetails (@RequestParam(name = "id") Long id, HttpServletRequest request) {

        String username = request.getHeader("username");
        Optional<User> user = userRepository.findOneByUsername(username);
        String userId = String.valueOf(user.get().getId());
        String authority = userAuthorityService.getAuthority(userId);
        if(authority.equals("ROLE_ADMIN")) {
            Schedule data = scheduleRepository.findOneById(id);
            return new ResponseEntity<>(data, HttpStatus.OK);
        }
        throw new AccessDeniedException();
    }

    @PostMapping("v1/schedule/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createSchedule (@RequestBody ScheduleDTO schedule, HttpServletRequest request){
        String username = request.getHeader("username");
        Optional<User> user = userRepository.findOneByUsername(username);
        String userId = String.valueOf(user.get().getId());
        String authority = userAuthorityService.getAuthority(userId);
        if(authority.equals("ROLE_ADMIN")) {
            scheduleService.CreateSchedule(schedule);
            return;
        }
        throw new AccessDeniedException();
    }
    @GetMapping("v1/lesson")
    public List<LessonDetailResp> getAllLesson( HttpServletRequest request) {
        return scheduleService.GetAllLesson();
    }

    @GetMapping("v1/day")
    public List<DayDetailResp> getAllDay(HttpServletRequest request) {
        return scheduleService.GetAllDay();
    }

}
