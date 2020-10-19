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

    @Column(name = "efficency")
    private Long efficency;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "tutor_subject",
            joinColumns = {@JoinColumn(name = "id_tutor")},
            inverseJoinColumns = {@JoinColumn(name = "id_subject")}
    )
    private Set<Subject> subject = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "id_literacy")
    private Literacy literacy;

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

    public void addSubject(Subject subject1)
    {
        this.subject.add(subject1);
        subject1.getTutorDetails().add(this);
    }

    public Literacy getLiteracy() {
        return literacy;
    }

    public void setLiteracy(Literacy literacy) {
        this.literacy = literacy;
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
