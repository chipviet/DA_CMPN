package com.doan.cnpm.controller;

import com.doan.cnpm.domain.Course;
import com.doan.cnpm.domain.User;
import com.doan.cnpm.repositories.CourseRepository;
import com.doan.cnpm.repositories.UserRepository;
import com.doan.cnpm.service.CourseService;
import com.doan.cnpm.service.dto.CourseDTO;
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
public class CourseController {
    private final Logger log = LoggerFactory.getLogger(CourseController.class);
    private final CourseRepository courseRepository;
    private CourseService courseService;
    private final UserRepository userRepository;

    private final UserAuthorityService userAuthorityService;

    public CourseController(CourseRepository courseRepository, CourseService courseService, UserRepository userRepository, UserAuthorityService userAuthorityService) {
        this.courseRepository = courseRepository;
        this.courseService = courseService;
        this.userRepository = userRepository;
        this.userAuthorityService = userAuthorityService;
    }

    @GetMapping(value="/v1/course")
    public List<Course> getALlCourses(HttpServletRequest request){
        return courseRepository.findAll();
    }

    @GetMapping("v1/course/details")
    public ResponseEntity<Course> getCourseDetails (@RequestParam(name = "id") Long id, HttpServletRequest request) {
        Course data = courseRepository.findOneById(id);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("v1/course/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCourse (@RequestBody CourseDTO course, HttpServletRequest request){
        String username = request.getHeader("username");
        Optional<User> user = userRepository.findOneByUsername(username);
        String userId = String.valueOf(user.get().getId());
        String authority = userAuthorityService.getAuthority(userId);
        if(authority.equals("ROLE_ADMIN")) {
            courseService.CreateCourse(course);
            return;
        }
        throw new AccessDeniedException();
    }

    @PutMapping("v1/course/update")
    @ResponseStatus(HttpStatus.UPGRADE_REQUIRED)
    public void updateCourse (@RequestBody CourseDTO course, HttpServletRequest request, @RequestParam(name = "id") Long id )  {
        String username = request.getHeader("username");
        Optional<User> user = userRepository.findOneByUsername(username);
        String userId = String.valueOf(user.get().getId());
        String authority = userAuthorityService.getAuthority(userId);
        if(authority.equals("ROLE_ADMIN")) {
            courseService.UpdateCourse(course, id);
            return;
        }
        throw new AccessDeniedException();
    }

    @DeleteMapping("v1/course/delete/{id}")
    public void deleteCourse(HttpServletRequest request,@RequestParam(name = "id") Long id)
    {
        String username = request.getHeader("username");
        Optional<User> user = userRepository.findOneByUsername(username);
        String userId = String.valueOf(user.get().getId());
        String authority = userAuthorityService.getAuthority(userId);
        if(authority.equals("ROLE_ADMIN")) {
            courseService.DeleteCourse(id);
            return;
        }
        throw new AccessDeniedException();
    }
}
