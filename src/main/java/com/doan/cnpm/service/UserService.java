package com.doan.cnpm.service;

import com.doan.cnpm.domain.Authority;
import com.doan.cnpm.domain.User;
import com.doan.cnpm.service.TutorDetailsService;
import com.doan.cnpm.domain.enumeration.UserStatus;
import com.doan.cnpm.repositories.AuthorityRepository;
import com.doan.cnpm.repositories.UserRepository;
import com.doan.cnpm.security.AuthoritiesConstants;
import com.doan.cnpm.service.dto.RegisterUserDTO;
import com.doan.cnpm.service.dto.UserDetailsDTO;
import com.doan.cnpm.service.exception.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private TutorDetailsService tutorDetailsService;
    private AuthorityRepository authorityRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository, TutorDetailsService tutorDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.tutorDetailsService = tutorDetailsService;
    }

    public User registerUser(RegisterUserDTO dto) {

        System.out.println("registerUser");
        Optional<User> existingUser = userRepository.findOneByUsername(dto.getUsername().toLowerCase());

       if(existingUser.isPresent()) {
           throw new UsernameAlreadyUsedException();
       }
        Optional<User> email = userRepository.findByEmail(dto.getEmail());

        if(email.isPresent()) {
            throw new EmailAlreadyExistException();
        }
        Optional<User> phoneNumber = userRepository.findByPhoneNumber(dto.getPhoneNumber());

        if(phoneNumber.isPresent()) {
            throw new PhoneNumberAlreadyExist();
        }


        User newUser = new User();
        newUser.setUsername(dto.getUsername().toLowerCase());
        String encryptedPassword = passwordEncoder.encode(dto.getPassword());
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(dto.getFirstName());
        newUser.setLastName(dto.getLastName());

        newUser.setEmail(dto.getEmail());
        newUser.setGender(dto.getGender());
        newUser.setPhoto(dto.getPhoto());
        newUser.setAddress(dto.getAddress());
        newUser.setLatitude(dto.getLatitude());
        newUser.setLongitude(dto.getLongitude());
        newUser.setIntroduction(dto.getIntroduction());
        newUser.setPhoneNumber(dto.getPhoneNumber());
        newUser.setDateOfBirth(dto.getDateOfBirth());
        newUser.setStatus(UserStatus.INACTIVE);
        newUser.setCreationDate(LocalDate.now());
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(dto.getAuthority()).ifPresent(authorities::add);
        newUser.setAuthorities(authorities);
        userRepository.save(newUser);
        System.out.println("Created information for ewallet User");
        return  newUser;
    }

    public User getUserDetails(String username) throws UserNotFoundException, UserIsInactiveException {

        User user = userRepository.findOneByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username:" + username));

        return user;
    }

    public List<UserDetailsDTO> getAllUser(){
        List<UserDetailsDTO> data = new ArrayList<>();
        List<User> users = userRepository.findAll();
        for(User user:users){
            UserDetailsDTO newUser = new UserDetailsDTO();
            newUser.setId(user.getId());
            newUser.setUsername(user.getUsername());
            newUser.setEmail(user.getEmail());
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            newUser.setAddress(user.getAddress());
            newUser.setGender(user.getGender());
            newUser.setIntroduction(user.getIntroduction());
            newUser.setDateOfBirth(user.getDateOfBirth());
            newUser.setPhoneNumber(user.getPhoneNumber());
            newUser.setPhoto(user.getPhoto());
            newUser.setStatus(user.getStatus());
            newUser.setAuthority(user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet()).iterator().next());
            data.add(newUser);
        }
        return data;
    }

    public UserDetailsDTO getUserDetailsById(Long id, String authority) throws UserNotFoundException, UserIsInactiveException {

        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found with id:" + id));

        UserDetailsDTO newUser = new UserDetailsDTO();
        newUser.setId(user.getId());
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setAddress(user.getAddress());
        newUser.setGender(user.getGender());
        newUser.setIntroduction(user.getIntroduction());
        newUser.setDateOfBirth(user.getDateOfBirth());
        newUser.setPhoneNumber(user.getPhoneNumber());
        newUser.setPhoto(user.getPhoto());
        newUser.setStatus(user.getStatus());
        newUser.setAuthority(user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet()).iterator().next());
        return newUser;
    }


    public User updateUser(RegisterUserDTO userDTO,Optional<User> user){
        user.get().setFirstName(userDTO.getFirstName());
        user.get().setLastName(userDTO.getLastName());
        user.get().setAddress(userDTO.getAddress());
        user.get().setDateOfBirth(userDTO.getDateOfBirth());
        user.get().setIntroduction(userDTO.getIntroduction());
        user.get().setEmail(userDTO.getEmail());
        user.get().setPhoneNumber(userDTO.getPhoneNumber());
        user.get().setGender(userDTO.getGender());
        user.get().setLatitude(userDTO.getLatitude());
        user.get().setLongitude(userDTO.getLongitude());
        user.get().setPhoto(userDTO.getPhoto());
        //user.get().setStatus(userDTO.getStatus());

        userRepository.save(user.get());
        return user.get();
    }

    public String changeUserStatus(Optional<User> user,String status){

        if(UserStatus.valueOf(status)!=null){
            UserStatus userStatus = UserStatus.valueOf(status);
            user.get().setStatus(userStatus);
        }
        userRepository.save(user.get());
        return "change " + user.get().getUsername() + " status : " +status;
    }

    public String deleteUser(User user){
        if(user!= null){
            //tutorDetailsService.DeleteTutorDetails(user.getUsername());
            user.removeAuthorities();
            userRepository.delete(user);
            System.out.println(user);
            return "Delete success User with username "+ user.getUsername() +" !";
        }
        return "Delete fail !";
    }
}

