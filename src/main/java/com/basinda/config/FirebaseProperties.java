package com.basinda.config;

import org.springframework.core.io.Resource;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "gcp.firebase")
public class FirebaseProperties {

    private Resource serviceAccount;

    public Resource getServiceAccount(){
        return serviceAccount;
    }

    public void setServiceAccount(Resource serviceAccount){
        this.serviceAccount = serviceAccount;
    }
}