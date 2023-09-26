package com.basinda.api.user;

import org.springframework.http.HttpStatus;
import com.basinda.models.entity.Profession;
import com.basinda.services.ProfessionService;
import org.springframework.http.ResponseEntity;
import com.basinda.models.response.ResponseHeader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProfessionController {
    private final ProfessionService professionService;

    public ProfessionController(ProfessionService professionService) {
        this.professionService = professionService;
    }

    public class Response extends ResponseHeader{
        public List<Profession> professions;
    }

    @GetMapping("/profession")
    public ResponseEntity<Response> getAllProfession(){
        Response response = new Response();
        List<Profession> professions = professionService.getAllProfession();
        if (professions != null){
            response.professions = professions;
        }
        else{
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setContent("Something went wrong.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}