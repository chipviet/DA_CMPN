package com.doan.cnpm.controller;

import com.doan.cnpm.domain.Need;
import com.doan.cnpm.domain.User;
import com.doan.cnpm.repositories.NeedRepository;
import com.doan.cnpm.repositories.UserRepository;
import com.doan.cnpm.service.NeedService;
import com.doan.cnpm.service.dto.NeedDTO;
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
    public List<Need> getALlNeeds(HttpServletRequest request){
        return needRepository.findAll();
    }

    @GetMapping("v1/need/details")
    public ResponseEntity<Need> getNeedDetails (@RequestParam(name = "id") Long id, HttpServletRequest request) {
        Need data = needRepository.findOneById(id);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("v1/need/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNeed (@RequestBody NeedDTO need, HttpServletRequest request){
        String username = request.getHeader("username");
        Optional<User> user = userRepository.findOneByUsername(username);
        String userId = String.valueOf(user.get().getId());
        String authority = userAuthorityService.getAuthority(userId);
        if(authority.equals("ROLE_ADMIN")) {
            needService.CreateNeed(need);
            return;
        }
        throw new AccessDeniedException();
    }

    @PutMapping("v1/need/update")
    @ResponseStatus(HttpStatus.UPGRADE_REQUIRED)
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
