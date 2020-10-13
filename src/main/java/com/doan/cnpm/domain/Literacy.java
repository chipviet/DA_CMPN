package com.doan.cnpm.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name ="literacy")
public class Literacy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "level")
    private String level;

    @OneToMany(mappedBy = "literacy",cascade = CascadeType.ALL)
    private Collection<TutorDetails> tutorDetails;

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

    public Collection<TutorDetails> getTutorDetails() {
        return tutorDetails;
    }

    public void setTutorDetails(Collection<TutorDetails> tutorDetails) {
        this.tutorDetails = tutorDetails;
    }
}
