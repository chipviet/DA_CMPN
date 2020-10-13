package com.doan.cnpm.domain;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * An authority (a security role) used by Spring Security.
 */
@Entity
@Table(name = "user_literacy")

public class UserLiteracy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "literacy_id")
    private String literacyId;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLiteracyId() {
        return literacyId;
    }

    public void setLiteracyId(String literacyId) {
        this.literacyId = literacyId;
    }

    @Override
    public String toString() {
        return "UserAuthority{" +
                "}";
    }
}
