package com.doan.cnpm.service;

import com.doan.cnpm.domain.TutorDetails;
import com.doan.cnpm.domain.User;
import com.doan.cnpm.repositories.TutorDetailsRepository;
import com.doan.cnpm.repositories.UserRepository;
import com.doan.cnpm.service.dto.TutorDetailsDTO;
import com.doan.cnpm.service.exception.TutorNotFoundException;
import com.doan.cnpm.service.response.TutorDetailsResp;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TutorDetailsService {

    private final TutorDetailsRepository tutorDetailsRepository;

    private final UserRepository userRepository;

    public TutorDetailsService(TutorDetailsRepository tutorDetailsRepository, UserRepository userRepository)
    {
        this.tutorDetailsRepository = tutorDetailsRepository;
        this.userRepository = userRepository;
    }

    public TutorDetails CreateTutorDetails(TutorDetailsDTO tutor, String username)
    {
        User user = userRepository.findOneByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username:" + username));
        TutorDetails newTutor = new TutorDetails();
        newTutor.setEfficency(tutor.getEfficency());
        newTutor.setLiteracy(tutor.getLiteracy());
        newTutor.setUsername(username);

        tutorDetailsRepository.save(newTutor);


        return newTutor;
    }
    public TutorDetails UpdateTutorDetails(TutorDetailsDTO tutor,String username){
        TutorDetails Tutor = tutorDetailsRepository.findOneByUsername(username);

        System.out.println("Tutor");
        System.out.println(Tutor);
        Tutor.setEfficency(tutor.getEfficency());
        Tutor.setLiteracy(tutor.getLiteracy());

        tutorDetailsRepository.save(Tutor);

        return Tutor;
    }

    public void DeleteTutorDetails(String username){
        tutorDetailsRepository.deleteByUsername(username);
    }
}
