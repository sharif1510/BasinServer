package com.basinda.api.admin;

import org.springframework.http.HttpStatus;
import com.google.firebase.messaging.Message;
import org.springframework.http.ResponseEntity;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.google.firebase.messaging.FirebaseMessagingException;

@RestController
public class FirebasePublisherController {
    private final FirebaseMessaging fcm;

    public FirebasePublisherController(FirebaseMessaging fcm){
        this.fcm = fcm;
    }

    @PostMapping("/clients/{token}")
    public ResponseEntity<String> postToClient(@RequestBody String message, @PathVariable("token") String token)
        throws FirebaseMessagingException {
        Message msg = Message.builder()
                .setToken(token)
                .putData("body", message)
                .build();

        String id = fcm.send(msg);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(id);
    }
}