package com.doan.cnpm.controller;

import com.doan.cnpm.repositories.LiteracyRepository;
import com.doan.cnpm.service.LiteracyService;
import com.doan.cnpm.service.response.LiteracyDetailResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://haimai.ddns.net:9090", maxAge = 3600)
@RestController
@RequestMapping("/api/edu")
@Transactional
public class LiteracyController {

    private final Logger log = LoggerFactory.getLogger(LiteracyController.class);

    private final LiteracyRepository literacyRepository;

    private final LiteracyService literacyService;

    public LiteracyController(LiteracyRepository literacyRepository, LiteracyService literacyService) {
        this.literacyRepository = literacyRepository;
        this.literacyService = literacyService;
    }

    @GetMapping(value="/v1/literacy")
    public List<LiteracyDetailResp> getALlSubject(){
        return literacyService.GetAllLiteracy();
    }
}
