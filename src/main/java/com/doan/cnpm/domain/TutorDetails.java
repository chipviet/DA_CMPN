package com.doan.cnpm.domain;


import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

//@Data
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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "tutor_subject",
            joinColumns = {@JoinColumn(name = "id_tutor")},
            inverseJoinColumns = {@JoinColumn(name = "id_subject")}
    )
    private Set<Subject> subject = new HashSet<>();

    public Set<Subject> getSubject() {
        return subject;
    }

    public void setSubject(Set<Subject> subject) {
        this.subject = subject;
    }



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

    public void addSubject(Subject subject1)
    {
        this.subject.add(subject1);
        subject1.getTutorDetails().add(this);
    }

    public void removeSubject(Subject subject1)
    {
        this.getSubject().remove(subject1);
        subject1.getTutorDetails().remove(this);
    }

    public void removeSubject()
    {
        for (Subject subject1 : new HashSet<>(subject)){
            removeSubject(subject1);
        }
    }


}
