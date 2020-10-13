package com.doan.cnpm.service;

import com.doan.cnpm.domain.Subject;
import com.doan.cnpm.repositories.SubjectRepository;
import com.doan.cnpm.service.dto.SubjectDTO;
import com.doan.cnpm.service.response.SubjectDetailsResp;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public Subject CreateSubject(SubjectDTO subject){
        Subject newSubject = new Subject();
        newSubject.setNameSubject(subject.getNameSubject());
        subjectRepository.save(newSubject);
        return newSubject;
    }

    public Subject UpdateSubject(SubjectDTO subject, Long id){
        Subject subject1 = subjectRepository.findOneById(id);

        subject1.setNameSubject(subject.getNameSubject());
        subjectRepository.save(subject1);
        return subject1;
    }

    public void DeleteSubject(Long id){
        subjectRepository.deleteById(id);
    }

    public List<SubjectDetailsResp> getAllSubject(){
        List<SubjectDetailsResp> data = new ArrayList<>();
        List<Subject> subjects = subjectRepository.findAll();
        for(Subject subject :subjects){
            SubjectDetailsResp subjectDetailsResp = new SubjectDetailsResp();
            subjectDetailsResp.setId(subject.getId());
            subjectDetailsResp.setNameSubject(subject.getNameSubject());
            data.add(subjectDetailsResp);
        }
        return data;
    }

    public SubjectDetailsResp getSubjectDetail(Long id){
        SubjectDetailsResp data = new SubjectDetailsResp();
        Subject subject = subjectRepository.findOneById(id);
        data.setId(subject.getId());
        data.setNameSubject(subject.getNameSubject());
        return data;
    }
}
