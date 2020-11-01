package com.doan.cnpm.service.response;

import javax.persistence.Column;
import java.util.HashSet;
import java.util.Set;

public class LiteracyDetailResp {
    private Long id;

    private String level;

   //private Set<String> firstName = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

}
