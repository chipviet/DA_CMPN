package com.doan.cnpm.domain;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name ="tutor_details")
public class TutorDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "literacy")
    private String literacy;

    @Column(name = "efficency")
    private Long efficency;

    @Column(name = "username")
    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUsername(){return username;}

    public void setUsername(String username){this.username = username; }

}
