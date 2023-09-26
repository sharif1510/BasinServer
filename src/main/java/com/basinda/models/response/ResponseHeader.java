package com.basinda.models.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class ResponseHeader {
    public boolean status;
    public String content;
    public HttpStatus statusCode;
    public ResponseHeader(){
        status = true;
        content = "";
        statusCode = HttpStatus.OK;
    }
}