package com.doan.cnpm.service;

import com.doan.cnpm.domain.Literacy;
import com.doan.cnpm.domain.Subject;
import com.doan.cnpm.domain.TutorDetails;
import com.doan.cnpm.domain.User;
import com.doan.cnpm.repositories.LiteracyRepository;
import com.doan.cnpm.repositories.SubjectRepository;
import com.doan.cnpm.repositories.TutorDetailsRepository;
import com.doan.cnpm.repositories.UserRepository;
import com.doan.cnpm.service.dto.TutorDetailsDTO;
import com.doan.cnpm.service.exception.SubjectNotFoundException;
import com.doan.cnpm.service.exception.TutorNotFoundException;
import com.doan.cnpm.service.response.TutorDetailsResp;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TutorDetailsService {

    private final TutorDetailsRepository tutorDetailsRepository;

    private final UserRepository userRepository;

    private final LiteracyRepository literacyRepository;

    private final SubjectRepository subjectRepository ;

//    public TutorDetailsService(TutorDetailsRepository tutorDetailsRepository, UserRepository userRepository, SubjectRepository subjectRepository) {
//        this.tutorDetailsRepository = tutorDetailsRepository;
//        this.userRepository = userRepository;
//        this.subjectRepository = subjectRepository;
//    }


    public TutorDetailsService(TutorDetailsRepository tutorDetailsRepository, UserRepository userRepository, LiteracyRepository literacyRepository, SubjectRepository subjectRepository) {
        this.tutorDetailsRepository = tutorDetailsRepository;
        this.userRepository = userRepository;
        this.literacyRepository = literacyRepository;
        this.subjectRepository = subjectRepository;
    }

    public TutorDetails CreateTutorDetails(TutorDetailsDTO tutor, String username)
    {
        //User user = userRepository.findOneByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username:" + username));
        TutorDetails newTutor = new TutorDetails();
        newTutor.setEfficency(tutor.getEfficency());
        Optional<Literacy> literacy = literacyRepository.findById((tutor.getLiteracy()));
        newTutor.setLiteracy(literacy.get());
        newTutor.setUsername(username);

        System.out.println("toi day");

        newTutor.setSubject(new HashSet<>());
        tutor.getSubject().stream().forEach(idSubject ->{
            Subject subject = subjectRepository.findOneById((Long.parseLong(idSubject)));
            System.out.println("subject");
            System.out.println(subject);
            if(subject == null){
                subject = new Subject();
                subject.setTutorDetails(new HashSet<>());
                try {
                    throw  new SubjectNotFoundException("Not found Subject with name: "+idSubject);
                } catch (SubjectNotFoundException e) {
                    e.printStackTrace();
                }
            }

            newTutor.addSubject(subject);
        });
        tutorDetailsRepository.save(newTutor);
//        TutorDetailsDTO tutorDTO = new TutorDetailsDTO();
//        tutorDTO.setEfficency(newTutor.getEfficency());
//        tutorDTO.setLiteracy(newTutor.getLiteracy());
//        tutorDTO.setSubject(newTutor.getSubject().stream().map(Subject::getNameSubject).collect(Collectors.toSet()));

        return newTutor;
    }
    public TutorDetails UpdateTutorDetails(TutorDetailsDTO tutor,String username){
        TutorDetails Tutor = tutorDetailsRepository.findOneByUsername(username);
        Tutor.setEfficency(tutor.getEfficency());//http://haimai.ddns.net:9090Tutor.getSubject().clear();
        if(Tutor.getSubject()==null){
            Tutor.setSubject(new HashSet<>());
        }
        tutor.getSubject().stream().forEach(idSubject -> {
            Subject subject = subjectRepository.findOneById(Long.parseLong(idSubject));
            Tutor.addSubject(subject);
        });

        Optional<Literacy> literacy = literacyRepository.findById(tutor.getLiteracy());
        Tutor.setLiteracy(literacy.get());

        tutorDetailsRepository.save(Tutor);

        return Tutor;
    }

    public void DeleteTutorDetails(String username){
        TutorDetails Tutor = tutorDetailsRepository.findOneByUsername(username);
        if(Tutor != null){
            Tutor.removeSubject();
            tutorDetailsRepository.deleteByUsername(username);
        }
    }


    public List<TutorDetailsResp> getAllTutorDetails(){
        List<TutorDetailsResp> data = new ArrayList<>();
        List<TutorDetails> tutors = tutorDetailsRepository.findAll();
        for (TutorDetails tutorDetail : tutors) {
            TutorDetailsResp tutor = new TutorDetailsResp();
            tutor.setId(tutorDetail.getId());
            tutor.setUsername(tutorDetail.getUsername());
            tutor.setLiteracy(tutorDetail.getLiteracy().getLevel());
            tutor.setEfficency(tutorDetail.getEfficency());
            tutor.setSubject(tutorDetail.getSubject().stream().map(Subject::getNameSubject).collect(Collectors.toSet()));
            data.add(tutor);
        }
        return data;
    }

    public TutorDetailsResp getTutorDetails(String username)throws TutorNotFoundException{
        TutorDetailsResp data = new TutorDetailsResp();
        TutorDetails tutor = tutorDetailsRepository.findOneByUsername(username);
        if(tutor ==null)
        {
            throw new TutorNotFoundException();
        }
        data.setId(tutor.getId());
        data.setUsername(tutor.getUsername());
        data.setLiteracy(tutor.getLiteracy().getLevel());
        data.setEfficency(tutor.getEfficency());
        data.setSubject(tutor.getSubject().stream().map(Subject::getNameSubject).collect(Collectors.toSet()));
        return  data;
    }
}
