package com.basinda.api.admin;

import org.springframework.http.HttpStatus;
import com.basinda.models.entity.Profession;
import com.basinda.services.ProfessionService;
import org.springframework.http.ResponseEntity;
import com.basinda.models.response.ResponseHeader;
import com.basinda.models.request.ProfessionRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class JobController {
    private final ProfessionService professionService;

    public JobController(ProfessionService professionService) {
        this.professionService = professionService;
    }

    public class Response extends ResponseHeader{

    }

    @PostMapping("/profession")
    public ResponseEntity<Response> createProfession(@RequestBody ProfessionRequest request){
        Response response = new Response();
        Profession profession = professionService.createProfession(request);
        if (profession != null){
            response.setStatusCode(HttpStatus.CREATED);
            response.setContent("Profession created successfully.");
        }
        else{
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setContent("Something went wrong.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}