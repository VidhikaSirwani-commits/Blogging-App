package com.codemine.blog_app_apis.exceptions;

import com.codemine.blog_app_apis.payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//RestControllerAdvice is a combintion of @ControllerAdvice and @ResponseBody
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        String message= ex.getMessage();
        // can add more details if we want in the APIResponse class by which it will give more details
        //eg date and time and many ore things
        ApiResponse apiResponse=new ApiResponse(message, false);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }
}
