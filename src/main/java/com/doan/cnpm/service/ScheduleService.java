package com.doan.cnpm.service;

import com.doan.cnpm.domain.Day;
import com.doan.cnpm.domain.Lesson;
import com.doan.cnpm.domain.Schedule;
import com.doan.cnpm.repositories.DayRepository;
import com.doan.cnpm.repositories.LessonRepository;
import com.doan.cnpm.repositories.ScheduleRepository;
import com.doan.cnpm.service.dto.ScheduleDTO;
import com.doan.cnpm.service.response.DayDetailResp;
import com.doan.cnpm.service.response.LessonDetailResp;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final DayRepository dayRepository;

    private final LessonRepository lessonRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, DayRepository dayRepository, LessonRepository lessonRepository) {
        this.scheduleRepository = scheduleRepository;
        this.dayRepository = dayRepository;
        this.lessonRepository = lessonRepository;
    }

    public Schedule CreateSchedule(ScheduleDTO schedule) {
        Schedule newSchedule = new Schedule();
//        newSchedule.setWeekday(schedule.getWeekday());
//        newSchedule.setLesson(schedule.getLesson());
//        scheduleRepository.save(newSchedule);
        return newSchedule;
    }
    public List<LessonDetailResp> GetAllLesson(){
        List<LessonDetailResp> data = new ArrayList<>();
        List<Lesson> Lessons = lessonRepository.findAll();
        for (Lesson lesson : Lessons) {
            LessonDetailResp lessonDetailResp = new LessonDetailResp();
            lessonDetailResp.setId(lesson.getId());
            lessonDetailResp.setLesson(lesson.getLesson());
            data.add(lessonDetailResp);
        }
        return data;
    }
    public List<DayDetailResp> GetAllDay(){
        List<DayDetailResp> data = new ArrayList<>();
        List<Day> days = dayRepository.findAll();
        for (Day day : days) {
            DayDetailResp dayDetailResp = new DayDetailResp();
            dayDetailResp.setId(day.getId());
            dayDetailResp.setDay(day.getDay());
            data.add(dayDetailResp);
        }
        return data;
    }
}
