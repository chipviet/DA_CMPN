package com.doan.cnpm.service.response;

public class RequestTutorResp {
    private Long id;

    private NeedDetailsResp needDetailsResp;

    private String nameTutor;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NeedDetailsResp getNeedDetailsResp() {
        return needDetailsResp;
    }

    public void setNeedDetailsResp(NeedDetailsResp needDetailsResp) {
        this.needDetailsResp = needDetailsResp;
    }

    public String getNameTutor() {
        return nameTutor;
    }

    public void setNameTutor(String nameTutor) {
        this.nameTutor = nameTutor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
