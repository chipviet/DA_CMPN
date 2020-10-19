package com.doan.cnpm.service;

import com.doan.cnpm.domain.Schedule;
import com.doan.cnpm.repositories.ScheduleRepository;
import com.doan.cnpm.service.dto.ScheduleDTO;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository)
    {
        this.scheduleRepository = scheduleRepository;
    }

    public Schedule CreateSchedule(ScheduleDTO schedule) {
        Schedule newSchedule = new Schedule();
//        newSchedule.setWeekday(schedule.getWeekday());
//        newSchedule.setLesson(schedule.getLesson());
//        scheduleRepository.save(newSchedule);
        return newSchedule;
    }
}
