package com.doan.cnpm.controller;

import com.doan.cnpm.domain.Need;
import com.doan.cnpm.domain.User;
import com.doan.cnpm.repositories.NeedRepository;
import com.doan.cnpm.repositories.UserRepository;
import com.doan.cnpm.service.NeedService;
import com.doan.cnpm.service.RequestTutorService;
import com.doan.cnpm.service.dto.NeedDTO;
import com.doan.cnpm.service.dto.RequestDTO;
import com.doan.cnpm.service.exception.AccessDeniedException;
import com.doan.cnpm.service.exception.NeedNotFoundException;
import com.doan.cnpm.service.response.NeedDetailsResp;
import com.doan.cnpm.service.response.RequestTutorResp;
import com.doan.cnpm.service.response.TutorDetailsResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
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
public class NeedController {
    private final Logger log = LoggerFactory.getLogger(NeedController.class);
    private final NeedRepository needRepository;
    private final NeedService needService;
    private final UserRepository userRepository;
    private final RequestTutorService requestTutorService;

    private final UserAuthorityService userAuthorityService;

    public NeedController(NeedRepository needRepository,
                          NeedService needService,
                          UserRepository userRepository,
                          RequestTutorService requestTutorService,
                          UserAuthorityService userAuthorityService) {
        this.needRepository = needRepository;
        this.needService = needService;
        this.userRepository = userRepository;
        this.requestTutorService = requestTutorService;
        this.userAuthorityService = userAuthorityService;
    }

    @GetMapping(value="/v1/need")
    public List<NeedDetailsResp> getALlNeeds(HttpServletRequest request){
        String username = request.getHeader("username");
        Optional<User> user = userRepository.findOneByUsername(username);
        String userId = String.valueOf(user.get().getId());
        String authority = userAuthorityService.getAuthority(userId);
        if(authority.equals("ROLE_TUTOR")||authority.equals("ROLE_STUDENT")||authority.equals("ROLE_ADMIN")){
            return needService.getAll();
        }

        throw new AccessDeniedException();
    }

    @GetMapping("v1/need/details")
    public NeedDetailsResp getNeedDetails (@RequestParam(name = "id") Long id) {
        NeedDetailsResp data =  needService.getNeedDetail(id);
        if(data == null) {
            throw new NeedNotFoundException();
        }
       return data;
    }

    @PostMapping("v1/need/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNeed (@RequestBody NeedDTO need, HttpServletRequest request){
        try{
            String username = request.getHeader("username");
            Optional<User> user = userRepository.findOneByUsername(username);
            String userId = String.valueOf(user.get().getId());
            String authority = userAuthorityService.getAuthority(userId);
            if(authority.equals("ROLE_STUDENT")) {
                needService.CreateNeed(need,user.get().getId());
                return;
            }
            else {
                throw new AccessDeniedException();
            }
        } catch (AccessDeniedException e){
            throw  e;
        }
    }

    @PutMapping("v1/need/update")
    //@ResponseStatus(HttpStatus.UPGRADE_REQUIRED)
    public void updateNeed (@RequestBody NeedDTO need, HttpServletRequest request, @RequestParam(name = "id") Long id )  {
        String username = request.getHeader("username");
        Optional<User> user = userRepository.findOneByUsername(username);
        String userId = String.valueOf(user.get().getId());
        String authority = userAuthorityService.getAuthority(userId);
        if(authority.equals("ROLE_STUDENT")) {
            needService.UpdateNeed(need, id);
            return;
        }
        throw new AccessDeniedException();
    }

    @DeleteMapping("v1/need/delete/{id}")
    public void deleteNeed(HttpServletRequest request,@RequestParam(name = "idNeed") Long idNeed)
    {
        String username = request.getHeader("username");
        Optional<User> user = userRepository.findOneByUsername(username);
        String userId = String.valueOf(user.get().getId());
        String authority = userAuthorityService.getAuthority(userId);
        if(authority.equals("ROLE_ADMIN")) {
            needService.DeleteNeed(idNeed);
            return;
        }
        throw new AccessDeniedException();
    }

    @GetMapping(value="/v1/need/request")
    public List<RequestTutorResp> getAllRequest(@RequestParam(name="idNeed")Long idNeed, HttpServletRequest request){
        String username = request.getHeader("username");
        Optional<User> user = userRepository.findOneByUsername(username);
        String userId = String.valueOf(user.get().getId());
        String authority = userAuthorityService.getAuthority(userId);
        if(authority.equals("ROLE_ADMIN")){
            return requestTutorService.getAllRequestTutor();
        }
        else if(authority.equals("ROLE_TUTOR")){
            return requestTutorService.getRequestTutorByIdTutor(user.get().getId());
        }
        else if(authority.equals("ROLE_STUDENT")) {
            return requestTutorService.getRequestTutorByIdNeed(idNeed);
        }
        throw new AccessDeniedException();
    }

    @PostMapping("v1/need/request/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createRequestTutor (HttpServletRequest request,@RequestParam(name="idNeed")Long idNeed) {
        String username = request.getHeader("username");
        Optional<User> user = userRepository.findOneByUsername(username);
        String userId = String.valueOf(user.get().getId());
        String authority = userAuthorityService.getAuthority(userId);
        if (authority.equals("ROLE_TUTOR")) {
            requestTutorService.createRequestTutor(user.get().getId(),idNeed);
        }
        throw new AccessDeniedException();
    }

    @PutMapping("v1/need/request/update")
    public void updateRequest (HttpServletRequest request, @RequestParam(name = "idRequest") Long idRequest, @RequestBody RequestDTO requestDTO)  {
        String username = request.getHeader("username");
        Optional<User> user = userRepository.findOneByUsername(username);
        String userId = String.valueOf(user.get().getId());
        String authority = userAuthorityService.getAuthority(userId);
        if(authority.equals("ROLE_STUDENT")) {
            requestTutorService.updateRequestTutor(requestDTO.getStatus(),idRequest);
            return;
        }
        throw new AccessDeniedException();
    }
    //here
    @GetMapping("v1/need/request-list")
    public List<TutorDetailsResp> getRequestTutorList(HttpServletRequest request,@RequestParam(name = "idNeed")Long idNeed){
        String username = request.getHeader("username");
        Optional<User> user = userRepository.findOneByUsername(username);
        String userId = String.valueOf(user.get().getId());
        String authority = userAuthorityService.getAuthority(userId);
        if(authority.equals("ROLE_STUDENT")) {
            return requestTutorService.getRequestTutorList(idNeed);
        }
        throw new AccessDeniedException();
    }
}
