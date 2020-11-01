package com.doan.cnpm.service;

import com.doan.cnpm.domain.*;
import com.doan.cnpm.repositories.*;
import com.doan.cnpm.service.dto.NeedDTO;
import com.doan.cnpm.service.exception.ScheduleHasBeenDuplicatedException;
import com.doan.cnpm.service.response.NeedDetailsResp;
import com.doan.cnpm.service.response.ScheduleDetailResp;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class NeedService {
    private final NeedRepository needRepository;
    private final SubjectRepository subjectRepository;
    private final ScheduleRepository scheduleRepository;
    private final DayRepository dayRepository;
    private final LessonRepository lessonRepository;

    public NeedService(NeedRepository needRepository, SubjectRepository subjectRepository,
                       ScheduleRepository scheduleRepository, DayRepository dayRepository, LessonRepository lessonRepository) {
        this.needRepository = needRepository;
        this.subjectRepository = subjectRepository;
        this.scheduleRepository = scheduleRepository;
        this.dayRepository = dayRepository;
        this.lessonRepository = lessonRepository;
    }

//    public List<NeedDetailsResp> getAll() {
//       List<Need> needs = needRepository.findAll();
//       List<NeedDetailsResp> data = new ArrayList<>();
//        for (Need need: needs) {
//             NeedDetailsResp needDetailsResp = new NeedDetailsResp();
//             needDetailsResp.setId(need.getId());
//             needDetailsResp.setIdUser(need.getIdUser());
//             needDetailsResp.setLevel(need.getLevel());
//             needDetailsResp.setPlace(need.getPlace());
//             needDetailsResp.setSchedule(needDetailsResp.getSchedule());
//             needDetailsResp.setStatus(need.getStatus());
//             needDetailsResp.setTuition(need.getTuition());
//             Subject subject = subjectRepository.findOneById(need.getSubject());
//             needDetailsResp.setSubject(subject.getNameSubject());
//             data.add(needDetailsResp);
//        }
//        return data;
//    }

    public List<NeedDetailsResp> getAllNeed() {
        List<Need> needs = needRepository.findAll();
        List<NeedDetailsResp> data = new ArrayList<>();
        for (Need need: needs) {

            if(need.getType().equals("OPEN_NEED")) {
                NeedDetailsResp needDetailsResp = new NeedDetailsResp();
                needDetailsResp.setId(need.getId());
                needDetailsResp.setIdUser(need.getIdUser());
                needDetailsResp.setLevel(need.getLevel());
                needDetailsResp.setPlace(need.getPlace());
                needDetailsResp.setSchedule(needDetailsResp.getSchedule());
                needDetailsResp.setStatus(need.getStatus());
                needDetailsResp.setTuition(need.getTuition());
                Subject subject = subjectRepository.findOneById(need.getSubject());
                needDetailsResp.setSubject(subject.getNameSubject());
                data.add(needDetailsResp);
            }
        }
        return data;
    }

    public List<NeedDetailsResp> getAll() {
        List<Need> needs = needRepository.findAll();
        List<NeedDetailsResp> data = new ArrayList<>();
        for (Need need: needs) {
                NeedDetailsResp needDetailsResp = new NeedDetailsResp();
                needDetailsResp.setId(need.getId());
                needDetailsResp.setIdUser(need.getIdUser());
                needDetailsResp.setLevel(need.getLevel());
                needDetailsResp.setPlace(need.getPlace());
                List<ScheduleDetailResp> scheduleList = new ArrayList<>();
            for (Schedule schedule : need.getSchedule()) {
                ScheduleDetailResp scheduleDetail = new ScheduleDetailResp();
                scheduleDetail.setDay(schedule.getDay().getDay());
                scheduleDetail.setLesson(schedule.getLessons().getLesson());
                scheduleList.add(scheduleDetail);
            }
                needDetailsResp.setSchedule(scheduleList);
                needDetailsResp.setStatus(need.getStatus());
                needDetailsResp.setTuition(need.getTuition());
                Subject subject = subjectRepository.findOneById(need.getSubject());
                needDetailsResp.setSubject(subject.getNameSubject());
                data.add(needDetailsResp);
        }
        return data;
    }

    public NeedDetailsResp getNeedDetail(Long id) {
        Need need = needRepository.findOneById(id);
        NeedDetailsResp needDetailsResp = new NeedDetailsResp();
        needDetailsResp.setId(need.getId());
        needDetailsResp.setIdUser(need.getIdUser());
        needDetailsResp.setLevel(need.getLevel());
        needDetailsResp.setPlace(need.getPlace());
        List<ScheduleDetailResp> scheduleList = new ArrayList<>();
        for (Schedule schedule : need.getSchedule()) {
            ScheduleDetailResp scheduleDetail = new ScheduleDetailResp();
            scheduleDetail.setDay(schedule.getDay().getDay());
            scheduleDetail.setLesson(schedule.getLessons().getLesson());
            scheduleList.add(scheduleDetail);
        }needDetailsResp.setStatus(need.getStatus());
        needDetailsResp.setTuition(need.getTuition());
        Subject subject = subjectRepository.findOneById(need.getSubject());
        needDetailsResp.setSubject(subject.getNameSubject());
        return needDetailsResp;
    }

    public Need CreateNeed(NeedDTO need, Long idUser){
        try {
            Need newNeed = new Need();
            newNeed.setIdUser(idUser);
            newNeed.setLevel(need.getLevel());
            newNeed.setSubject(need.getSubject());
            newNeed.setPlace(need.getPlace());
            newNeed.setStatus(false);
            newNeed.setType("OPEN_NEED");
            newNeed.setTuition(need.getTuition());
            need.getSchedule().stream().forEach(dayLesson->{
                Optional<Day> day = dayRepository.findById(dayLesson.getId_day());
                Optional<Lesson> lesson =lessonRepository.findById(dayLesson.getId_lesson());
                List<Schedule> schedules = scheduleRepository.findByIdDayIdLesson(day.get(),lesson.get());
                System.out.println("schedule");
                System.out.println(schedules);
                if(!schedules.isEmpty()) {
                    if (schedules.get(0) != null) {
                        schedules.get(0).getNeeds().stream().forEach(user -> {
                            if (user.getIdUser() == idUser) {
                                throw new ScheduleHasBeenDuplicatedException(); //exception here
                            }
                        });
                    }
                }
                if(schedules.isEmpty()){
                    Schedule newSchedule = new Schedule();
                    newSchedule.setDay(day.get());
                    newSchedule.setLessons(lesson.get());
                    scheduleRepository.save(newSchedule);
                    schedules = scheduleRepository.findByIdDayIdLesson(day.get(),lesson.get());
                }
                if(newNeed.getSchedule()==null)
                    newNeed.setSchedule(new HashSet<>());
                for (Schedule schedule: schedules
                ) {
                    newNeed.addSchedule(schedule);
                }

            });
            needRepository.save(newNeed);
            return newNeed;
        }
        catch (Exception e) {
            throw e;
        }

    }

    public Need UpdateNeed(NeedDTO need, Long id){
        Need need1 = needRepository.findOneById(id);

        need1.setIdUser(need1.getIdUser());
        need1.setLevel(need1.getLevel());
        need1.setSubject(need1.getSubject());
        need1.setPlace(need1.getPlace());
        need1.setSchedule(need1.getSchedule());
        need1.setStatus(need1.getStatus());
        needRepository.save(need1);
        return need1;
    }

    public void DeleteNeed(Long id){
        needRepository.deleteById(id);
    }
}
