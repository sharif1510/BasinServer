package com.basinda.api.user;

import com.basinda.models.entity.Flat;
import com.basinda.models.request.user.ApproveFlatRequest;
import com.basinda.models.request.user.RentFlatRequest;
import com.basinda.services.FlatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.basinda.models.response.ResponseHeader;

import java.util.List;

@RestController
@RequestMapping("/flats")
public class FlatController {
    private final FlatService flatService;

    public FlatController(FlatService flatService) {
        this.flatService = flatService;
    }

    public class Response extends ResponseHeader{

    }

    public class FlatResponse extends ResponseHeader{
        public List<Flat> flats;
    }

    @GetMapping("/all")
    public ResponseEntity<FlatResponse> getAllFlats(){
        FlatResponse response = new FlatResponse();
        response.flats = flatService.read();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<Response> createFlat(@RequestBody Flat model){

        Response response = new Response();
        Flat flat = flatService.createFlat(model);
        if (flat == null){
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setStatus(true);
            response.setContent("Something went wrong please try again.");
        }
        else{
            response.setStatusCode(HttpStatus.CREATED);
            response.setStatus(true);
            response.setContent("Flat created successfully.");
        }

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/rent/{id}")
    public ResponseEntity<Response> rentFlat(@PathVariable("id") Long id, @RequestBody RentFlatRequest request){
        Response response = new Response();
        flatService.rentFlat(id, request);
        response.setContent("Flat rent successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/approve/{id}")
    public ResponseEntity<Response> approveFlat(@PathVariable("id") Long id, @RequestBody ApproveFlatRequest request){
        Response response = new Response();
        flatService.approveFlat(id, request);
        response.setContent("Flat approved successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlatResponse> getUserFlats(@PathVariable("id") Long id){
        FlatResponse response = new FlatResponse();
        response.flats = flatService.getFlatForUsers(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}