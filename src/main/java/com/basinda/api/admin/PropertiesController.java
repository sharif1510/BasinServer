package com.basinda.api.admin;

import org.springframework.http.HttpStatus;
import com.basinda.services.PropertiesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PropertiesController {
    private final PropertiesService propertiesService;

    public PropertiesController(PropertiesService propertiesService) {
        this.propertiesService = propertiesService;
    }

    @PostMapping("/properties")
    public ResponseEntity<String> propertiesSave(){
        return ResponseEntity.status(HttpStatus.CREATED).body("Done");
    }
}