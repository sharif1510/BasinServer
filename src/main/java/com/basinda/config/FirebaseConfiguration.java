package com.basinda.config;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.context.annotation.Bean;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.io.InputStream;
import java.io.IOException;

@Configuration
@EnableConfigurationProperties(FirebaseProperties.class)
public class FirebaseConfiguration {

    private final FirebaseProperties firebaseProperties;
    
    public FirebaseConfiguration(FirebaseProperties firebaseProperties){
        this.firebaseProperties = firebaseProperties;
    }

    @Bean
    GoogleCredentials googleCredentials(){
        try {
            if (firebaseProperties.getServiceAccount() != null){
                try (InputStream is = firebaseProperties.getServiceAccount().getInputStream()) {
                    return GoogleCredentials.fromStream(is);
                }
            }
            else{
                return GoogleCredentials.getApplicationDefault();
            }
        }
        catch (IOException ex){
            throw new RuntimeException();
        }
    }

    @Bean
    FirebaseApp firebaseApp(GoogleCredentials credentials){
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .build();
        return FirebaseApp.initializeApp(options);
    }

    @Bean
    FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp){

        return FirebaseMessaging.getInstance(firebaseApp);
    }
}