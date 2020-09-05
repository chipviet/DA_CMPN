package com.doan.cnpm.controller;



import com.doan.cnpm.domain.Course;
import com.doan.cnpm.repositories.CourseRepository;
import com.doan.cnpm.service.CourseService;
import com.doan.cnpm.service.dto.CourseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.lang.Integer.parseInt;

@RestController
@RequestMapping("/api/edu")
@Transactional
public class CourseController {

//    private final Logger log = LoggerFactory.getLogger(CourseController.class);

    private final CourseRepository courseRepository;

    private CourseService courseService;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
        this.courseService = courseService;
    }

    @GetMapping("v1/course")
    public List<Course> getAllClasses() {
        return courseRepository.findAll();
    }

    @GetMapping("v1/course/details")
    public Course getClass (HttpServletRequest request) throws ClassNotFoundException {

        Integer id = parseInt(request.getHeader("id"));

        Course data = courseRepository.findOneById(id);

        return data;
    }

    @PostMapping("v1/course/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCourse(@RequestBody CourseDTO course, HttpServletRequest request){
        String id = request.getHeader("id");
        System.out.println(id);
        courseService.CreateCourse(course, id);
    }

    @PutMapping("v1/course/update")
    @ResponseStatus(HttpStatus.UPGRADE_REQUIRED)
    public void updateCourse(@RequestBody CourseDTO course, HttpServletRequest request)  {
        String id = request.getHeader("id");
        System.out.println(id);
        courseService.UpdateCourse(course, id);
    }

    @DeleteMapping("v1/course/delete")
    public void deleteCourse(HttpServletRequest request)
    {
        String id = request.getHeader("id");
        courseService.DeleteCourse(id);
    }

}
