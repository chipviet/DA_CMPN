package com.doan.cnpm.controller;

import com.doan.cnpm.domain.Subject;
import com.doan.cnpm.domain.User;
import com.doan.cnpm.service.response.SubjectDetailsResp;
import com.doan.cnpm.repositories.SubjectRepository;
import com.doan.cnpm.repositories.UserRepository;
import com.doan.cnpm.service.SubjectService;
import com.doan.cnpm.service.dto.SubjectDTO;
import com.doan.cnpm.service.exception.AccessDeniedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.doan.cnpm.service.UserAuthorityService;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://haimai.ddns.net:9090", maxAge = 3600)
@RestController
@RequestMapping("/api/edu")
@Transactional
public class SubjectController {
    private final Logger log = LoggerFactory.getLogger(SubjectController.class);
    private final SubjectRepository subjectRepository;
    private SubjectService subjectService;
    private final UserRepository userRepository;

    private final UserAuthorityService userAuthorityService;

    public SubjectController(SubjectRepository subjectRepository, SubjectService subjectService, UserRepository userRepository, UserAuthorityService userAuthorityService) {
        this.subjectRepository = subjectRepository;
        this.subjectService = subjectService;
        this.userRepository = userRepository;
        this.userAuthorityService = userAuthorityService;
    }
    
    @GetMapping(value="/v1/subject")
    public List<SubjectDetailsResp> getALlSubject(HttpServletRequest request){
        return subjectService.getAllSubject();
    }

    @GetMapping("v1/subject/details")
    public SubjectDetailsResp getSubjectDetails (@RequestParam(name = "id") Long id, HttpServletRequest request) {
       return subjectService.getSubjectDetail(id);
    }

    @PostMapping("v1/subject/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createSubject (@RequestBody SubjectDTO subject, HttpServletRequest request){
        String username = request.getHeader("username");
        Optional<User> user = userRepository.findOneByUsername(username);
        String userId = String.valueOf(user.get().getId());
        String authority = userAuthorityService.getAuthority(userId);
        if(authority.equals("ROLE_ADMIN")) {
            subjectService.CreateSubject(subject);
            return;
        }
        throw new AccessDeniedException();
    }

    @PutMapping("v1/subject/update")
    //@ResponseStatus(HttpStatus.UPGRADE_REQUIRED)
    public void updateSubject (@RequestBody SubjectDTO subject, HttpServletRequest request,@RequestParam(name = "id") Long id )  {
        String username = request.getHeader("username");
        Optional<User> user = userRepository.findOneByUsername(username);
        String userId = String.valueOf(user.get().getId());
        String authority = userAuthorityService.getAuthority(userId);
        if(authority.equals("ROLE_ADMIN")) {
            subjectService.UpdateSubject(subject, id);
            return;
        }
        throw new AccessDeniedException();
    }

    @DeleteMapping("v1/subject/delete")
    public void deleteSubject(HttpServletRequest request,@RequestParam(name = "id") Long id)
    {
        String username = request.getHeader("username");
        Optional<User> user = userRepository.findOneByUsername(username);
        String userId = String.valueOf(user.get().getId());
        String authority = userAuthorityService.getAuthority(userId);
        if(authority.equals("ROLE_ADMIN")) {
            subjectService.DeleteSubject(id);
            return;
        }
        throw new AccessDeniedException();
    }
}
