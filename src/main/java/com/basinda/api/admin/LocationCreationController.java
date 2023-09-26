package com.basinda.api.admin;

import org.springframework.http.HttpStatus;
import com.basinda.services.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.basinda.models.response.ResponseHeader;
import com.basinda.models.request.admin.UpozilaRequest;
import com.basinda.models.request.admin.DistrictRequest;
import com.basinda.models.request.admin.DivisionRequest;
import com.basinda.models.request.admin.PourosovaRequest;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class LocationCreationController {
    private final LocationService locationService;

    public LocationCreationController(LocationService locationService) {
        this.locationService = locationService;
    }

    public class Response extends ResponseHeader {

    }

    public class LocationResponse extends ResponseHeader{
        public List data;
    }

    @GetMapping("/divisions")
    public ResponseEntity<LocationResponse> getDivisions(){
        LocationResponse response = new LocationResponse();
        response.data = locationService.getAllDivisions();
        System.out.println(response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/districts")
    public ResponseEntity<LocationResponse> getDistricts(){
        LocationResponse response = new LocationResponse();
        response.data = locationService.getAllDistricts();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/upozilas")
    public ResponseEntity<LocationResponse> getUpozilas(){
        LocationResponse response = new LocationResponse();
        response.data = locationService.getAllUpozilas();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/pourosovas")
    public ResponseEntity<LocationResponse> getPoursovas(){
        LocationResponse response = new LocationResponse();
        response.data = locationService.getAllPourosovas();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/create/division")
    public ResponseEntity<Response> createDivision(@RequestBody DivisionRequest model){
        Response response = new Response();
        String createdDivision = locationService.createDivision(model);
        if (createdDivision.equalsIgnoreCase("created")){
            response.setStatusCode(HttpStatus.CREATED);
            response.setStatus(true);
            response.setContent("Division created successfully.");
        }
        else{
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setStatus(true);
            response.setContent("Something went wrong please try again.");
        }
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/create/district")
    public ResponseEntity<Response> createDistrict(@RequestBody DistrictRequest model){
        Response response = new Response();
        String createdDistrict = locationService.createDistrict(model);
        if (createdDistrict.equalsIgnoreCase("created")){
            response.setStatusCode(HttpStatus.CREATED);
            response.setStatus(true);
            response.setContent("District created successfully.");
        }
        else{
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setStatus(true);
            response.setContent("Something went wrong please try again.");
        }
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/create/upozila")
    public ResponseEntity<Response> createUpozila(@RequestBody UpozilaRequest model){
        Response response = new Response();
        String createdUpozila = locationService.createUpozila(model);
        if (createdUpozila.equalsIgnoreCase("created")){
            response.setStatusCode(HttpStatus.CREATED);
            response.setStatus(true);
            response.setContent("Upozila created successfully.");
        }
        else{
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setStatus(true);
            response.setContent("Something went wrong please try again.");
        }
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/create/pourosova")
    public ResponseEntity<Response> createPourosova(@RequestBody PourosovaRequest model){
        Response response = new Response();
        String createdPourosova = locationService.createPourosova(model);
        if (createdPourosova.equalsIgnoreCase("created")){
            response.setStatusCode(HttpStatus.CREATED);
            response.setStatus(true);
            response.setContent("Pourosova created successfully.");
        }
        else{
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setStatus(true);
            response.setContent("Something went wrong please try again.");
        }
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/division/{id}")
    public ResponseEntity<Response> deleteDivision(@PathVariable("id") Long id){
        Response response = new Response();
        locationService.deleteDivision(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/district/{id}")
    public ResponseEntity<Response> deleteDistrict(@PathVariable("id") Long id){
        Response response = new Response();
        locationService.deleteDistrict(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/upozila/{id}")
    public ResponseEntity<Response> deleteUpozila(@PathVariable("id") Long id){
        Response response = new Response();
        locationService.deleteUpozilla(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/pourosova/{id}")
    public ResponseEntity<Response> deletePourosova(@PathVariable("id") Long id){
        Response response = new Response();
        locationService.deletePourosova(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/edit/division/{id}")
    public ResponseEntity<Response> editDivision(@PathVariable("id") Long id, @RequestBody DivisionRequest model ){
        Response response = new Response();
        locationService.editDivision(id, model);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/edit/district/{id}")
    public ResponseEntity<Response> editDistrict(@PathVariable("id") Long id, @RequestBody DistrictRequest model ){
        Response response = new Response();
        locationService.editDistrict(id, model);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/edit/upozila/{id}")
    public ResponseEntity<Response> editUpozila(@PathVariable("id") Long id, @RequestBody UpozilaRequest model ){
        Response response = new Response();
        locationService.editUpozila(id, model);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/edit/pourosova/{id}")
    public ResponseEntity<Response> editPourosova(@PathVariable("id") Long id, @RequestBody PourosovaRequest model ){
        Response response = new Response();
        locationService.editPourosova(id, model);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}