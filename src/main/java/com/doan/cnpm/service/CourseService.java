package com.doan.cnpm.service;

import com.doan.cnpm.domain.*;
import com.doan.cnpm.repositories.CourseRepository;
import com.doan.cnpm.repositories.NeedRepository;
import com.doan.cnpm.repositories.TutorDetailsRepository;
import com.doan.cnpm.repositories.UserRepository;
import com.doan.cnpm.service.dto.CourseDTO;
import com.doan.cnpm.service.dto.TutorDetailsDTO;
import com.doan.cnpm.service.exception.JoinCourseDeniedException;
import com.doan.cnpm.service.exception.SubjectNotFoundException;
import com.doan.cnpm.service.exception.TutorNotFoundException;
import com.doan.cnpm.service.exception.UserNotFoundException;
import com.doan.cnpm.service.response.CourseDetailResp;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    private final NeedRepository needRepository;

    private final UserRepository userRepository;

    private final TutorDetailsRepository tutorDetailsRepository;

    public CourseService(CourseRepository courseRepository, NeedRepository needRepository, UserRepository userRepository, TutorDetailsRepository tutorDetailsRepository) {
        this.courseRepository = courseRepository;
        this.needRepository = needRepository;
        this.userRepository = userRepository;
        this.tutorDetailsRepository = tutorDetailsRepository;
    }

    public Course CreateCourse(Long idNeed, Long idUser){

        Need need = needRepository.findOneById(idNeed);
        Course newCourse = new Course();
        newCourse.setIdNeed(idNeed);
        if(need.getType().equals("OPEN_NEED")) {
            newCourse.setIdTutor(idUser);
            User user = userRepository.getOne(need.getIdUser());
            if(user == null){
                user = new User();
                user.setCourses(new HashSet<>());
                try {
                    throw  new UserNotFoundException();
                } catch (UserNotFoundException e) {
                    e.printStackTrace();
                }
            }
            newCourse.addStudent(user);
        }
        else if(need.getType().equals("OPEN_COURSE")){
            User user = userRepository.getOne(idUser);
            if(user == null){
                user = new User();
                user.setCourses(new HashSet<>());
                try {
                    throw  new UserNotFoundException();
                } catch (UserNotFoundException e) {
                    e.printStackTrace();
                }
            }
            newCourse.setIdTutor(need.getIdUser());
            newCourse.addStudent(user);

        }
        courseRepository.save(newCourse);
        return newCourse;
    }

    public Course UpdateCourse(CourseDTO course, Long id){
        Course data = courseRepository.findOneById(id);
        User tutor = userRepository.getOne(course.getIdTutor());
        if (tutor.getAuthorities().equals("ROLE_TUTOR")){
            data.getStudent().clear();
            data.setIdNeed(course.getIdNeed());
            data.setIdTutor(course.getIdTutor());
            if(data.getStudent()==null){
                data.setStudent(new HashSet<>());
            }
            course.getStudent().stream().forEach(idStudent->{
                Optional<User> student = userRepository.findById(idStudent);
                if(student.get().getAuthorities().equals("ROLE_STUDENT")){
                    data.addStudent(student.get());
                }
            });

            courseRepository.save(data);
        }
        return data;
    }

    public Course JoinCourse(Long idCourse,Long idStudent)
    {
        Course course1 = courseRepository.findOneById(idCourse);
        boolean check = true;
        User student = userRepository.getOne(idStudent);
        if(course1.getStudent().size()<5 && course1.getStudent().equals(student)){
           course1.addStudent(student);
        }
        else  throw new JoinCourseDeniedException();
        courseRepository.save(course1);
        return course1;
    }

    public Course OutCourse(Long idCourse,Long idStudent){

        Course course1 = courseRepository.findOneById(idCourse);
        course1.getStudent().stream().forEach(hasAlready ->{
            if (hasAlready.getId() != idStudent) return;
            User student = userRepository.getOne(idStudent);
            course1.removeStudent(student);
        });
        courseRepository.save(course1);
        return course1;
    }


    public String DeleteCourse(Long id){
        Course course = courseRepository.findOneById(id);
        if(course != null){
            course.removeStudent();
            courseRepository.deleteById(id);
            return "Delete success course with id "+ id +" !";
        }
        return "Delete fail !";
    }

    public List<CourseDetailResp> getAllCourse(){
        List<CourseDetailResp> data = new ArrayList<>();
        List<Course> courses = courseRepository.findAll();
        for (Course course : courses) {
            CourseDetailResp course1 = new CourseDetailResp();
            course1.setId(course.getId());
            course1.setIdNeed(course.getIdNeed());
            Optional<TutorDetails> tutorDetails = tutorDetailsRepository.findById(course.getIdTutor());
            course1.setIdTutor(tutorDetails.get().getUsername());
            course1.setStudent(course.getStudent().stream().map(User::getUsername).collect(Collectors.toSet()));
            data.add(course1);
        }
        return data;
    }

    public CourseDetailResp getCourseDetails(Long id)throws TutorNotFoundException{
        CourseDetailResp data = new CourseDetailResp();
        Course course = courseRepository.findOneById(id);
        if(course ==null)
        {
            throw new TutorNotFoundException();
        }
        data.setId(course.getId());
        Optional<TutorDetails> tutorDetails = tutorDetailsRepository.findById(course.getIdTutor());
        data.setIdTutor(tutorDetails.get().getUsername());
        data.setIdNeed(course.getIdNeed());
        data.setStudent(course.getStudent().stream().map(User::getUsername).collect(Collectors.toSet()));
        return  data;
    }

}
