package com.doan.cnpm.controller;



import com.doan.cnpm.domain.TutorDetails;
import com.doan.cnpm.domain.User;
import com.doan.cnpm.repositories.TutorDetailsRepository;
import com.doan.cnpm.repositories.UserRepository;
import com.doan.cnpm.service.TutorDetailsService;
import com.doan.cnpm.service.dto.TutorDetailsDTO;
import com.doan.cnpm.service.exception.TutorNotFoundException;
import com.doan.cnpm.service.response.TutorDetailsResp;
import org.apache.tomcat.util.http.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/edu")
@Transactional
public class TutorDetailsController {


    private final Logger log = LoggerFactory.getLogger(TutorDetailsController.class);

    private final TutorDetailsRepository tutorDetailsRepository;

    private final UserRepository userRepository;

    private TutorDetailsService tutorDetailsService;

    public TutorDetailsController (TutorDetailsRepository tutorDetailsRepository, UserRepository userRepository,TutorDetailsService tutorDetailsService) {
        this.tutorDetailsRepository = tutorDetailsRepository;
        this.userRepository = userRepository;
        this.tutorDetailsService = tutorDetailsService;
    };

    @GetMapping("v1/tutor")
    public List<TutorDetails> getAllTutorDetails() {
        System.out.println("Vaooodaayy");
        return tutorDetailsRepository.findAll();
    }


    @GetMapping("v1/tutor/details")
    public TutorDetails getTutorDetails (HttpServletRequest request) throws TutorNotFoundException {

        String username = request.getHeader("username");

        TutorDetails data = tutorDetailsRepository.findOneById(username);

        return data;
    }
    
    @PostMapping("v1/tutor/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTutorDetails (@RequestBody TutorDetailsDTO tutor, HttpServletRequest request){
        String username = request.getHeader("username");
        System.out.println(username);
        tutorDetailsService.CreateTutorDetails(tutor, username);
    }

    @PutMapping("v1/tutor/update")
    @ResponseStatus(HttpStatus.UPGRADE_REQUIRED)
    public void updateTutorDetails (@RequestBody TutorDetailsDTO tutor, HttpServletRequest request)  {
        String username = request.getHeader("username");
        System.out.println(username);
        tutorDetailsService.UpdateTutorDetails(tutor, username);
    }

    @DeleteMapping("v1/tutor/delete")
    public void deleteTutorDetails(HttpServletRequest request)
    {
        String username = request.getHeader("username");
        tutorDetailsService.DeleteTutorDetails(username);
    }


}
