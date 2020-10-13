package com.doan.cnpm.controller;



import com.doan.cnpm.domain.Authority;
import com.doan.cnpm.domain.TutorDetails;
import com.doan.cnpm.domain.User;
import com.doan.cnpm.repositories.TutorDetailsRepository;
import com.doan.cnpm.repositories.UserAuthorityRepository;
import com.doan.cnpm.repositories.UserRepository;
import com.doan.cnpm.service.TutorDetailsService;
import com.doan.cnpm.service.UserAuthorityService;
import com.doan.cnpm.service.dto.TutorDetailsDTO;
import com.doan.cnpm.service.exception.AccessDeniedException;
import com.doan.cnpm.service.exception.TutorNotFoundException;
import com.doan.cnpm.service.response.TutorDetailsResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "http://haimai.ddns.net:9090", maxAge = 3600)
@RestController
@RequestMapping("/api/edu")
@Transactional
public class TutorDetailsController {

    private final Logger log = LoggerFactory.getLogger(TutorDetailsController.class);

    private final TutorDetailsRepository tutorDetailsRepository;

    private final UserRepository userRepository;

    private final UserAuthorityService userAuthorityService;

    private  TutorDetailsService tutorDetailsService;

    public TutorDetailsController (TutorDetailsRepository tutorDetailsRepository, UserRepository userRepository,TutorDetailsService tutorDetailsService, UserAuthorityService userAuthorityService) {
        this.tutorDetailsRepository = tutorDetailsRepository;
        this.userRepository = userRepository;
        this.tutorDetailsService = tutorDetailsService;
        this.userAuthorityService = userAuthorityService;
    };

    @GetMapping("v1/tutor")
    public List<TutorDetailsResp> getAllTutorDetails(HttpServletRequest request) {
        String username = request.getHeader("username");
        Optional<User> user = userRepository.findOneByUsername(username);
        String userId = String.valueOf(user.get().getId());
        String authority = userAuthorityService.getAuthority(userId);
        if(authority.equals("ROLE_TUTOR")||authority.equals("ROLE_ADMIN") ){
            return tutorDetailsService.getAllTutorDetails();
        }
        throw new AccessDeniedException();

    }


    @GetMapping("v1/tutor/details")
    public TutorDetailsResp getTutorDetails (HttpServletRequest request) throws TutorNotFoundException {

        String username = request.getHeader("username");

        TutorDetailsResp data = tutorDetailsService.getTutorDetails(username);

        return data;
    }
    
    @PostMapping("v1/tutor/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTutorDetails (@RequestBody TutorDetailsDTO tutor, HttpServletRequest request){

        String username = request.getHeader("username");
        Optional<User> user = userRepository.findOneByUsername(username);
        String userId = String.valueOf(user.get().getId());
        String authority = userAuthorityService.getAuthority(userId);
        System.out.println("authority");
        System.out.println(authority);
        if(authority.equals("ROLE_TUTOR")||authority.equals("ROLE_ADMIN" )){
            tutorDetailsService.CreateTutorDetails(tutor, username);
            return;
        }
        throw new AccessDeniedException();
    }

    @PutMapping("v1/tutor/update")
    //@ResponseStatus(HttpStatus.UPGRADE_REQUIRED)
    public void updateTutorDetails (@RequestBody TutorDetailsDTO tutor, HttpServletRequest request)  {
        String username = request.getHeader("username");
        Optional<User> user = userRepository.findOneByUsername(username);
        String userId = String.valueOf(user.get().getId());
        String authority = userAuthorityService.getAuthority(userId);
        if(authority.equals("ROLE_TUTOR")||authority.equals("ROLE_ADMIN" )){
            tutorDetailsService.UpdateTutorDetails(tutor, username);
        }
        throw new AccessDeniedException();
    }

    @DeleteMapping("v1/tutor/delete")
    public void deleteTutorDetails(HttpServletRequest request)
    {
        String username = request.getHeader("username");
        tutorDetailsService.DeleteTutorDetails(username);
    }


}
