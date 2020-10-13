package com.doan.cnpm.service;

import com.doan.cnpm.domain.Need;
import com.doan.cnpm.domain.Subject;
import com.doan.cnpm.repositories.NeedRepository;
import com.doan.cnpm.repositories.SubjectRepository;
import com.doan.cnpm.service.dto.NeedDTO;
import com.doan.cnpm.service.response.NeedDetailsResp;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NeedService {
    private final NeedRepository needRepository;
    private final SubjectRepository subjectRepository;

    public NeedService(NeedRepository needRepository, SubjectRepository subjectRepository) {
        this.needRepository = needRepository;
        this.subjectRepository = subjectRepository;
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
             needDetailsResp.setSchedule(needDetailsResp.getSchedule());
             needDetailsResp.setStatus(need.getStatus());
             needDetailsResp.setTuition(need.getTuition());
             Subject subject = subjectRepository.findOneById(need.getSubject());
             needDetailsResp.setSubject(subject.getNameSubject());
             data.add(needDetailsResp);
        }
        return data;
    }

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

    public List<NeedDetailsResp> getAllCourse() {
        List<Need> needs = needRepository.findAll();
        List<NeedDetailsResp> data = new ArrayList<>();
        for (Need need: needs) {
            if(need.getType().equals("OPEN_COURSE")) {
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

    public NeedDetailsResp getNeedDetail(Long id) {
        Need need = needRepository.findOneById(id);
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
        return needDetailsResp;
    }

    public Need CreateNeed(NeedDTO need, Long idUser,String type){
        Need newNeed = new Need();
        newNeed.setIdUser(idUser);
        newNeed.setLevel(need.getLevel());
        newNeed.setSubject(need.getSubject());
        newNeed.setPlace(need.getPlace());
        newNeed.setSchedule(need.getSchedule());
        newNeed.setStatus(false);
        newNeed.setType(type);
        newNeed.setTuition(need.getTuition());
        needRepository.save(newNeed);
        return newNeed;
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
