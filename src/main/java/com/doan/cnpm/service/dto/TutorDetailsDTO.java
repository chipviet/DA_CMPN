package com.doan.cnpm.service.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


public class TutorDetailsDTO {


    private Long id;

    @NotNull
    @NotBlank
    private Long efficency;

    @NotNull
    @NotBlank
    private Long literacy;

    @NotBlank
    private String username;

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

    public Long getLiteracy() {
        return literacy;
    }

    public void setLiteracy(Long literacy) {
        this.literacy = literacy;
    }

    public Long getEfficency() {
        return efficency;
    }

    public void setEfficency(Long efficency) {
        this.efficency = efficency;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
