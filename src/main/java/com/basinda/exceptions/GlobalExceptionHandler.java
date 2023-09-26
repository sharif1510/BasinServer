package com.basinda.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.basinda.models.response.ResponseHeader;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

    public class Response extends ResponseHeader {

    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<Response> unAuthorizedUser(AuthorizationException ex){
        Response response = new Response();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.setStatus(true);
        response.setContent(ex.getMessage());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Response> resourceNotFoundException(ResourceNotFoundException ex){
        Response response = new Response();
        response.setStatusCode(HttpStatus.NOT_FOUND);
        response.setStatus(true);
        response.setContent(ex.getMessage());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<Response> userAlreadyExistException(UserAlreadyExistException ex){
        Response response = new Response();
        response.setStatusCode(HttpStatus.CONFLICT);
        response.setStatus(true);
        response.setContent(ex.getMessage());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @ExceptionHandler(FileUploadFailedException.class)
    public ResponseEntity<Response> fileUploadFailedException(FileUploadFailedException ex){
        Response response = new Response();
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        response.setStatus(true);
        response.setContent(ex.getMessage());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @ExceptionHandler(UserRequestPendingAlreadyException.class)
    public ResponseEntity<Response> userRequestPendingAlreadyException(UserRequestPendingAlreadyException ex){
        Response response = new Response();
        response.setStatusCode(HttpStatus.CONFLICT);
        response.setStatus(true);
        response.setContent(ex.getMessage());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}