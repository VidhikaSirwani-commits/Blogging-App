package com.codemine.blog_app_apis.exceptions;

import lombok.Getter;
import lombok.Setter;

//we are extending it from RuntimeException because we are trying to create a checked exception
@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{

    String resourceName;
    String feildName;
    Integer feildvalue;

    public ResourceNotFoundException(String resourceName, String feildName, Integer feildvalue) {
        // using super() we are trying to create the message which we want to
        // try to create fromatted exception
        super(String.format("%s not found with %s : %s", resourceName,feildName,feildvalue));
        this.feildName = feildName;
        this.feildvalue = feildvalue;
        this.resourceName = resourceName;
    }
}
