package com.doan.cnpm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//@Data
@Entity
@Table(name ="needs")
public class Need {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_user")
    private Long idUser;

    @Column(name = "level")
    private Long level;

    @Column(name = "subject")
    private Long subject;

    @Column(name = "place")
    private String place;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "schedule_need",
            joinColumns = {@JoinColumn(name = "id_need")},
            inverseJoinColumns = {@JoinColumn(name = "id_schedule")}
    )
    private Set<Schedule> schedule = new HashSet<>();

    @Column(name = "status")
    private Boolean status;


    @Column(name = "type")
    private String type;

    @Column(name = "tuition")
    private Long tuition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Long getSubject() {
        return subject;
    }

    public void setSubject(Long subject) {
        this.subject = subject;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Set<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(Set<Schedule> schedule) {
        this.schedule = schedule;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getTuition() {
        return tuition;
    }

    public void setTuition(Long tuition) {
        this.tuition = tuition;
    }

    public void removeSchedule(Schedule schedule1)
    {
        this.getSchedule().remove(schedule1);
        schedule1.getNeeds().remove(this);
    }

    public void removeSubject()
    {
        for (Schedule schedule1 : new HashSet<>(schedule)){
            removeSchedule(schedule1);
        }
    }
    public void addSchedule(Schedule schedule1)
    {
        this.schedule.add(schedule1);
        schedule1.getNeeds().add(this);
    }

}
