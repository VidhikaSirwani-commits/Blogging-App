package com.codemine.blog_app_apis.exceptions;

import com.codemine.blog_app_apis.payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex){
        /*
        here for many feild we will get different exceptions so its like
        we will get many error if more than one feild is wrong
        so its like we are taking each feild's error message and putting in map along with msg
        */
        Map<String ,String> resp= new HashMap<>();
        // Now from the ex we will try to extract the feild =name and the message

        ex.getBindingResult().getAllErrors().forEach((error)->{
            //extract the feild name ie name, email which feild error we got
            // we have to typecaste the error to FeildError
            String message = error.getDefaultMessage();
            String feildName= ((FieldError)error).getField();
            resp.put(feildName,message);
        });
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.BAD_REQUEST);
    }
}
