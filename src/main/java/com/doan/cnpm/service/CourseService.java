package com.doan.cnpm.service;

import com.doan.cnpm.domain.Course;
import com.doan.cnpm.repositories.CourseRepository;
import com.doan.cnpm.service.dto.CourseDTO;
import org.springframework.stereotype.Service;

import static java.lang.Integer.parseInt;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository)
    {
        this.courseRepository = courseRepository;
    }

    public Course CreateCourse(CourseDTO course, String id)
    {
        Course newCourse = new Course();
        newCourse.setId(course.getId());
        newCourse.setId_student(course.getId_student());
        newCourse.setId_subject(course.getId_subject());
        newCourse.setLevel(course.getLevel());
        newCourse.setBasic_tuition(course.getBasic_tuition());
        newCourse.setId_tutor(course.getId_tutor());
        newCourse.setId_schedule(course.getId_schedule());

        courseRepository.save(newCourse);
        return newCourse;
    }
    public Course UpdateCourse(CourseDTO course, String id){
        Course Course = courseRepository.findOneById(parseInt(id));

        System.out.println("Course");
        System.out.println(Course);
        Course.setId(course.getId());
        Course.setId_student(course.getId_student());
        Course.setId_subject(course.getId_subject());
        Course.setLevel(course.getLevel());
        Course.setBasic_tuition(course.getBasic_tuition());
        Course.setId_tutor(course.getId_tutor());
        Course.setId_schedule(course.getId_schedule());

        courseRepository.save(Course);

        return Course;
    }

    public void DeleteCourse(String id){
        courseRepository.deleteById(parseInt(id));
    }
}
