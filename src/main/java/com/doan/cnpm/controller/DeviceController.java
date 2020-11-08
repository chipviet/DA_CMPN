package com.doan.cnpm.controller;


import com.doan.cnpm.domain.Device;
import com.doan.cnpm.repositories.DeviceRepository;
import com.doan.cnpm.service.DeviceService;
import com.doan.cnpm.service.exception.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/edu")
public class DeviceController {

    private final Logger log = LoggerFactory.getLogger(DeviceController.class);
    private static final String ENTITY_NAME = "device";

    private final DeviceService deviceService;
    private final DeviceRepository deviceRepository;

    public DeviceController(DeviceService deviceService, DeviceRepository deviceRepository) {
        this.deviceService = deviceService;
        this.deviceRepository = deviceRepository;
    }

    @PostMapping("/devices")
    public ResponseEntity<Device> createDevice(@Valid @RequestBody Device device) throws URISyntaxException {
        log.debug("REST request to save Device : {}", device);
        if (device.getId() != null) {
            throw new BadRequestAlertException("A new device cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Device result = deviceService.save(device);
        return ResponseEntity.created(new URI("/api/devices/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("Tutor Finder", true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PutMapping("/devices")
    public ResponseEntity<Device> updateDevice(@Valid @RequestBody Device device) throws URISyntaxException {
        log.debug("REST request to update Device : {}", device);
        if (device.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Device result = deviceService.save(device);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("Tutor Finder", true, ENTITY_NAME, device.getId().toString()))
                .body(result);
    }

    @GetMapping("/devices")
    public ResponseEntity<List<Device>> getAllDevices(HttpServletRequest request)  {
        log.debug("REST request to get a page of Devices");
        List<Device> data = deviceRepository.findAll();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/devices/{id}")
    public ResponseEntity<Device> getDevice(@PathVariable Long id) {
        log.debug("REST request to get Device : {}", id);
        Optional<Device> device = deviceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(device);
    }

    @DeleteMapping("/devices/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        log.debug("REST request to delete Device : {}", id);
        deviceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert("Tutor Finder", true, ENTITY_NAME, id.toString())).build();
    }
}
