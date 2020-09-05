package com.doan.cnpm.service;

import com.doan.cnpm.domain.User;
import com.doan.cnpm.domain.enumeration.UserStatus;
import com.doan.cnpm.repositories.UserRepository;
import com.doan.cnpm.service.dto.RegisterUserDTO;
import com.doan.cnpm.service.exception.UserIsInactiveException;
import com.doan.cnpm.service.exception.UserNotFoundException;
import com.doan.cnpm.service.exception.UsernameAlreadyUsedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;


@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public User registerUser(RegisterUserDTO dto) {

        System.out.println("registerUser");
        Optional<User> existingUser = userRepository.findOneByUsername(dto.getUsername().toLowerCase());

       if(existingUser.isPresent()) {
           throw new UsernameAlreadyUsedException();
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
        newUser.setIntroduction(dto.getIntroduction());
        newUser.setPhoneNumber(dto.getPhoneNumber());
        newUser.setDateOfBirth(dto.getDateOfBirth());

        newUser.setStatus(UserStatus.INACTIVE);
        newUser.setCreationDate(LocalDate.now());
        userRepository.save(newUser);
        System.out.println("Created information for ewallet User");
        return  newUser;
    }

    public User getUserDetails(String username) throws UserNotFoundException, UserIsInactiveException {

        User user = userRepository.findOneByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username:" + username));

        return user;
    }
}

