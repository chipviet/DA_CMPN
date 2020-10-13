package com.doan.cnpm.service.response;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SubjectDetailsResp {

    private Long id;

    @NotNull
    @NotBlank
    private String nameSubject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameSubject() {
        return nameSubject;
    }

    public void setNameSubject(String nameSubject) {
        this.nameSubject = nameSubject;
    }

}
