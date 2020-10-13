package com.doan.cnpm.controller;

import com.doan.cnpm.domain.User;
import com.doan.cnpm.repositories.UserRepository;
import com.doan.cnpm.service.UserAuthorityService;
import com.doan.cnpm.service.UserService;
import com.doan.cnpm.service.dto.RegisterUserDTO;
import com.doan.cnpm.service.dto.UserDetailsDTO;
import com.doan.cnpm.service.exception.AccessDeniedException;
import com.doan.cnpm.service.exception.UserIsInactiveException;
import com.doan.cnpm.service.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://haimai.ddns.net:9090", maxAge = 3600)
@RestController
@RequestMapping("/api/edu")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    private UserAuthorityService userAuthorityService;

    private UserRepository userRepository;


    public UserController( UserAuthorityService userAuthorityService, UserRepository userRepository ) {
        this.userAuthorityService = userAuthorityService;
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/v1/user/details")
    public  ResponseEntity<User> getUserDetails(HttpServletRequest request)
            throws UserNotFoundException, UserIsInactiveException {
        String username = request.getHeader("username");
        User userDetailsResp = userService.getUserDetails(username);

        return new ResponseEntity<>(userDetailsResp, HttpStatus.OK);
    }

    @GetMapping("/v1/user/details/{id}")
    public UserDetailsDTO getUserDetailsById(@PathVariable Long id, HttpServletRequest request) {
        log.debug("REST request to get TutorDetails : {}", id);
        String username = request.getHeader("username");
        Optional<User> user = userRepository.findOneByUsername(username);
        String userId = String.valueOf(user.get().getId());
        String authority = userAuthorityService.getAuthority(userId);
        if(authority.equals("ROLE_ADMIN")) {
            try {
                UserDetailsDTO resp = userService.getUserDetailsById(id,authority);
                return resp;
            } catch (UserIsInactiveException e) {
                e.printStackTrace();
            }
        }
        throw new AccessDeniedException();
    }

    @GetMapping("/v1/user")
    public  ResponseEntity<List<UserDetailsDTO>> getAllUser(HttpServletRequest request) throws UserNotFoundException, UserIsInactiveException {
        String username = request.getHeader("username");
        Optional<User> user = userRepository.findOneByUsername(username);
        String userId = String.valueOf(user.get().getId());
        String authority = userAuthorityService.getAuthority(userId);
        if(authority.equals("ROLE_ADMIN")) {
            List<UserDetailsDTO> resp = userService.getALlUser();
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
        throw new AccessDeniedException();
    }

    @PutMapping("v1/user/update")
    //@ResponseStatus(HttpStatus.UPGRADE_REQUIRED)
    public User updateUser(@RequestParam(name = "idUser")Long idUser, @RequestBody RegisterUserDTO userDTO,HttpServletRequest request ){
        String username = request.getHeader("username");
        Optional<User> user = userRepository.findOneByUsername(username);
        String userId = String.valueOf(user.get().getId());
        String authority = userAuthorityService.getAuthority(userId);
        if(authority.equals("ROLE_ADMIN")) {
            Optional<User> user1 = userRepository.findById(idUser);
            return userService.updateUser(userDTO,user1);
        }

        return userService.updateUser(userDTO,user);
    }

    @DeleteMapping("v1/user/delete")
    public Long deleteUser(@RequestParam(name = "idUser" )Long idUser,HttpServletRequest request){
        String username = request.getHeader("username");
        Optional<User> user = userRepository.findOneByUsername(username);
        String userId = String.valueOf(user.get().getId());
        System.out.println(userId);

        String authority = userAuthorityService.getAuthority(userId);
        if(authority.equals("ROLE_ADMIN")) {

            Optional<User> user1 = userRepository.findById(idUser);
            System.out.println(user1.get());
            userService.deleteUser(user1.get());
            return idUser;
            //return "Delete success";
        }
        return idUser;
        //userService.deleteUser(user.get());
        //return "Delete success";
    }

    @PutMapping("v1/user/changeStatus")
    @ResponseStatus(HttpStatus.UPGRADE_REQUIRED)
    public String updateUser(@RequestParam(name = "id")Long idUser,@RequestParam(name ="status") String status, HttpServletRequest request){
        String username = request.getHeader("username");
        Optional<User> user = userRepository.findOneByUsername(username);
        String userId = String.valueOf(user.get().getId());
        String authority = userAuthorityService.getAuthority(userId);
        if(authority.equals("ROLE_ADMIN")) {
            Optional<User> user1 = userRepository.findById(idUser);
            return userService.changeUserStatus(user1,status);
        }
        throw new AccessDeniedException();
    }
}
