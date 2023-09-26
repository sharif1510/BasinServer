package com.basinda.api.user;

import org.springframework.http.HttpStatus;
import com.basinda.services.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.basinda.models.response.ResponseHeader;

import java.util.List;

@RestController
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    public class Response extends ResponseHeader{
        public List data;
    }

    @GetMapping("/divisions")
    public ResponseEntity<Response> getAllDivisions(){
        Response response = new Response();
        response.data = locationService.readDivision();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/districts/{divisionId}")
    public ResponseEntity<Response> getAllDistrictsForDivision(@PathVariable Long divisionId){
        Response response = new Response();
        response.data = locationService.readDistrictForDivision(divisionId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/upozilas/{districtId}")
    public ResponseEntity<Response> getAllUpozilasForDistrict(@PathVariable Long districtId){
        Response response = new Response();
        response.data = locationService.readUpozilaForDistrict(districtId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/pourosovas/{upozilaId}")
    public ResponseEntity<Response> getAllPourosovasForDistrict(@PathVariable Long upozilaId){
        Response response = new Response();
        response.data = locationService.readPourosovaForUpozila(upozilaId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}