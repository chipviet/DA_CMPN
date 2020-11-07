package com.doan.cnpm.service.response;

import java.util.HashSet;
import java.util.Set;

public class CourseDetailResp {

    private Long id;

    private NeedDetailsResp needDetailsResp;

    private String nameTutor;

    private Set<String> student = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NeedDetailsResp getNeedDetailsResp() {
        return needDetailsResp;
    }

    public void setNeedDetailsResp(NeedDetailsResp needDetailsResp) {
        this.needDetailsResp = needDetailsResp;
    }

    public String getNameTutor() {
        return nameTutor;
    }

    public void setNameTutor(String nameTutor) {
        this.nameTutor = nameTutor;
    }


    public Set<String> getStudent() {
        return student;
    }

    public void setStudent(Set<String> student) {
        this.student = student;
    }
}
