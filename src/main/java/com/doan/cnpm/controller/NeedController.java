package com.doan.cnpm.controller;

import com.doan.cnpm.domain.Need;
import com.doan.cnpm.domain.User;
import com.doan.cnpm.repositories.NeedRepository;
import com.doan.cnpm.repositories.UserRepository;
import com.doan.cnpm.service.NeedService;
import com.doan.cnpm.service.dto.NeedDTO;
import com.doan.cnpm.service.exception.AccessDeniedException;
import com.doan.cnpm.service.exception.NeedNotFoundException;
import com.doan.cnpm.service.response.NeedDetailsResp;
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
public class NeedController {
    private final Logger log = LoggerFactory.getLogger(NeedController.class);
    private final NeedRepository needRepository;
    private NeedService needService;
    private final UserRepository userRepository;

    private final UserAuthorityService userAuthorityService;

    public NeedController(NeedRepository needRepository, NeedService needService, UserRepository userRepository, UserAuthorityService userAuthorityService) {
        this.needRepository = needRepository;
        this.needService = needService;
        this.userRepository = userRepository;
        this.userAuthorityService = userAuthorityService;
    }

    @GetMapping(value="/v1/need")
    public List<NeedDetailsResp> getALlNeeds(HttpServletRequest request){
        String username = request.getHeader("username");
        Optional<User> user = userRepository.findOneByUsername(username);
        String userId = String.valueOf(user.get().getId());
        String authority = userAuthorityService.getAuthority(userId);
        if(authority.equals("ROLE_ADMIN")) {
            return needService.getAll();
        }
        else if(authority.equals("ROLE_TUTOR")){
            return needService.getAllNeed();
        }
        else if(authority.equals("ROLE_STUDENT")){
            return needService.getAllCourse();
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
        String username = request.getHeader("username");
        Optional<User> user = userRepository.findOneByUsername(username);
        String userId = String.valueOf(user.get().getId());
        String authority = userAuthorityService.getAuthority(userId);
        String type = "";
        if(authority.equals("ROLE_TUTOR")) {
            type = "OPEN_COURSE";
        }
        else if(authority.equals("ROLE_STUDENT")){
            type = "OPEN_NEED";
        }
        if(type != ""){
            needService.CreateNeed(need,user.get().getId(),type);
            return;
        }
        throw new AccessDeniedException();
    }

    @PutMapping("v1/need/update")
    //@ResponseStatus(HttpStatus.UPGRADE_REQUIRED)
    public void updateNeed (@RequestBody NeedDTO need, HttpServletRequest request, @RequestParam(name = "id") Long id )  {
        String username = request.getHeader("username");
        Optional<User> user = userRepository.findOneByUsername(username);
        String userId = String.valueOf(user.get().getId());
        String authority = userAuthorityService.getAuthority(userId);
        if(authority.equals("ROLE_ADMIN")) {
            needService.UpdateNeed(need, id);
            return;
        }
        throw new AccessDeniedException();
    }

    @DeleteMapping("v1/need/delete/{id}")
    public void deleteNeed(HttpServletRequest request,@RequestParam(name = "id") Long id)
    {
        String username = request.getHeader("username");
        Optional<User> user = userRepository.findOneByUsername(username);
        String userId = String.valueOf(user.get().getId());
        String authority = userAuthorityService.getAuthority(userId);
        if(authority.equals("ROLE_ADMIN")) {
            needService.DeleteNeed(id);
            return;
        }
        throw new AccessDeniedException();
    }
}
