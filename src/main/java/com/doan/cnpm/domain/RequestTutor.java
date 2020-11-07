package com.doan.cnpm.domain;

import com.doan.cnpm.domain.enumeration.RequestStatus;

import javax.persistence.*;

@Entity
@Table(name ="request")
public class RequestTutor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_need")
    private Long idNeed;

    @Column(name = "id_tutor")
    private Long idTutor;

    @Column(name = "status")
    private RequestStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdNeed() {
        return idNeed;
    }

    public void setIdNeed(Long idNeed) {
        this.idNeed = idNeed;
    }

    public Long getIdTutor() {
        return idTutor;
    }

    public void setIdTutor(Long idTutor) {
        this.idTutor = idTutor;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }
}
