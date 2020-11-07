package com.doan.cnpm.service;

import com.doan.cnpm.domain.*;
import com.doan.cnpm.domain.enumeration.RequestStatus;
import com.doan.cnpm.domain.enumeration.UserStatus;
import com.doan.cnpm.repositories.*;
import com.doan.cnpm.service.response.NeedDetailsResp;
import com.doan.cnpm.service.response.RequestTutorResp;
import com.doan.cnpm.service.response.ScheduleDetailResp;
import com.doan.cnpm.service.response.TutorDetailsResp;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RequestTutorService {
    private final CourseRepository courseRepository;

    private final NeedRepository needRepository;

    private final UserRepository userRepository;

    private final TutorDetailsRepository tutorDetailsRepository;

    private final RequestTutorRepository requestTutorRepository;

    public RequestTutorService(CourseRepository courseRepository,
                               NeedRepository needRepository,
                               UserRepository userRepository,
                               RequestTutorRepository requestTutorRepository,
                               TutorDetailsRepository tutorDetailsRepository) {
        this.courseRepository = courseRepository;
        this.needRepository = needRepository;
        this.userRepository = userRepository;
        this.tutorDetailsRepository = tutorDetailsRepository;
        this.requestTutorRepository = requestTutorRepository;
    }

    public List<RequestTutorResp> getAllRequestTutor(){
        List<RequestTutorResp> data = new ArrayList<>();
        List<RequestTutor> requestTutors = requestTutorRepository.findAll();
        for (RequestTutor requestTutor: requestTutors) {
            data.add(getRequestTutor(requestTutor));
        }
        return data;
    }

    public List<RequestTutorResp> getRequestTutorByIdNeed(Long idNeed){
        List<RequestTutorResp> data = new ArrayList<>();
        List<RequestTutor> requestTutors = requestTutorRepository.findByIdNeed(idNeed);
        for (RequestTutor requestTutor: requestTutors) {
            data.add(getRequestTutor(requestTutor));
        }
        return data;
    }

    public List<RequestTutorResp> getRequestTutorByIdTutor(Long idTutor){
        List<RequestTutorResp> data = new ArrayList<>();
        List<RequestTutor> requestTutors = requestTutorRepository.findByIdTutor(idTutor);
        for (RequestTutor requestTutor: requestTutors) {
            data.add(getRequestTutor(requestTutor));
        }
        return data;
    }

    public void createRequestTutor(Long idTutor, Long idNeed){
        RequestTutor newRequestTutor = new RequestTutor();
        newRequestTutor.setIdNeed(idNeed);
        newRequestTutor.setIdTutor(idTutor);
        newRequestTutor.setStatus(RequestStatus.PENDING);
        requestTutorRepository.save(newRequestTutor);
    }

    public void updateRequestTutor(String status, Long idRequest){
        Optional<RequestTutor> requestTutor = requestTutorRepository.findById(idRequest);
        if(RequestStatus.valueOf(status)!=null){
            RequestStatus requestStatus = RequestStatus.valueOf(status);
            if(RequestStatus.ACCEPTED == requestStatus){
                List<RequestTutor> requestTutors = requestTutorRepository.findByIdNeed(requestTutor.get().getIdNeed());
                for (RequestTutor requestTt: requestTutors) {
                    requestTt.setStatus(RequestStatus.DENIED);
                    requestTutorRepository.save(requestTt);
                }
                requestTutor.get().setStatus(RequestStatus.ACCEPTED);
            }
        }
        requestTutorRepository.save(requestTutor.get());
    }

    private RequestTutorResp getRequestTutor(RequestTutor requestTutor){
        RequestTutorResp requestTutorResp = new RequestTutorResp();
        requestTutorResp.setId(requestTutor.getId());
        Optional<User> tutor = userRepository.findById(requestTutor.getIdTutor());
        TutorDetails tutorDetails = tutorDetailsRepository.findOneByUser(tutor.get());
        requestTutorResp.setNameTutor(tutorDetails.getUser().getUsername());
        Need need = needRepository.findOneById(requestTutor.getIdNeed());
        NeedDetailsResp needDetailsResp = new NeedDetailsResp();
        needDetailsResp.setId(need.getId());
        User user = userRepository.getOne(need.getIdUser());
        needDetailsResp.setNameUser(user.getUsername());
        needDetailsResp.setLevel(need.getLevel());
        needDetailsResp.setPlace(need.getPlace());
        needDetailsResp.setStatus(need.getStatus());
        needDetailsResp.setTuition(need.getTuition());
        requestTutorResp.setNeedDetailsResp(needDetailsResp);
        List<ScheduleDetailResp> scheduleList = new ArrayList<>();
        for (Schedule schedule : need.getSchedule()) {
            ScheduleDetailResp scheduleDetail = new ScheduleDetailResp();
            scheduleDetail.setDay(schedule.getDay().getDay());
            scheduleDetail.setLesson(schedule.getLessons().getLesson());
            scheduleList.add(scheduleDetail);
        }
        needDetailsResp.setSchedule(scheduleList);
        return requestTutorResp;
    }


    public List<TutorDetailsResp> getRequestTutorList(Long idNeed){
        List<TutorDetailsResp> data = new ArrayList<>();
        List<RequestTutor> requestTutors = requestTutorRepository.findByIdNeed(idNeed);
        for (RequestTutor requestTutor: requestTutors) {
            Optional<User> tutors = userRepository.findById(requestTutor.getIdTutor());
            TutorDetails tutorDetail = tutorDetailsRepository.findOneByUser(tutors.get());
            TutorDetailsResp tutor = new TutorDetailsResp();
            tutor.setId(tutorDetail.getId());
            tutor.setUser(tutorDetail.getUser());
            tutor.setLiteracy(tutorDetail.getLiteracy().getLevel());
            tutor.setEfficency(tutorDetail.getEfficency());
            tutor.setSubject(tutorDetail.getSubject().stream().map(Subject::getNameSubject).collect(Collectors.toSet()));
            data.add(tutor);
        }
        return data;
    }
}
