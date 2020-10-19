package com.doan.cnpm.service.response;

import com.doan.cnpm.domain.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

public class TutorDetailsResp {


    private Long id;

    @NotNull
    @NotBlank
    private Long efficency;

    @NotNull
    @NotBlank
    private String literacy;

    @NotBlank
    private User user;

    @NotNull
    @NotBlank
    private Set<String> subject = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<String> getSubject() {
        return subject;
    }

    public void setSubject(Set<String> subject) {
        this.subject = subject;
    }

    public String getLiteracy() {
        return literacy;
    }

    public void setLiteracy(String literacy) {
        this.literacy = literacy;
    }

    public Long getEfficency() {
        return efficency;
    }

    public void setEfficency(Long efficency) {
        this.efficency = efficency;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
