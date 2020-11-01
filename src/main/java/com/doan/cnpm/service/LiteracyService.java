package com.doan.cnpm.service;

import com.doan.cnpm.domain.Literacy;
import com.doan.cnpm.domain.Subject;
import com.doan.cnpm.domain.TutorDetails;
import com.doan.cnpm.domain.User;
import com.doan.cnpm.repositories.LiteracyRepository;
import com.doan.cnpm.repositories.TutorDetailsRepository;
import com.doan.cnpm.repositories.UserRepository;
import com.doan.cnpm.service.response.LiteracyDetailResp;
import com.doan.cnpm.service.response.TutorDetailsResp;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LiteracyService {

    private final TutorDetailsRepository tutorDetailsRepository;
    private final LiteracyRepository literacyRepository;
    private final UserRepository userRepository;

    public LiteracyService(TutorDetailsRepository tutorDetailsRepository, LiteracyRepository literacyRepository, UserRepository userRepository) {
        this.tutorDetailsRepository = tutorDetailsRepository;
        this.literacyRepository = literacyRepository;
        this.userRepository = userRepository;
    }

    public List<LiteracyDetailResp> GetAllLiteracy(){
        List<LiteracyDetailResp> data = new ArrayList<>();
        List<Literacy> literacies = literacyRepository.findAll();
        for (Literacy literacy : literacies) {
            LiteracyDetailResp literacyResp = new LiteracyDetailResp();
            literacyResp.setId(literacy.getId());
            literacyResp.setLevel(literacy.getLevel());
            data.add(literacyResp);
        }
        return data;
    }
}
