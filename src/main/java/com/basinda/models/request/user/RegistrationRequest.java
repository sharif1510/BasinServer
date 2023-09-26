package com.basinda.models.request.user;

import lombok.Data;
import com.basinda.models.eUserType;
import com.basinda.models.eGenderType;

import java.util.Date;

@Data
public class RegistrationRequest {
    private String nid;
    private String name;
    private String fatherName;
    private String motherName;
    private eGenderType genderType;
    private Date birthday;
    private String phone;
    private String email;
    private String profession;
    private String division;
    private String district;
    private String upozilla;
    private String pourosova;
    private String area;
    private String wordNo;
    private String postCode;
    private String holdingNumber;
    private eUserType userType;
    private String password;
    private String confirmPassword;
}
