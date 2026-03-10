package com.codemine.blog_app_apis.payloads;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    /*
    the annotations which we are using for validation if not correct it will throw the exception
    so now we will handle the exception in the global exception handling
    for global exception handler we will get MethodArgNotValidException
     */

    private Integer userId;
//    @NotNull //it checks if null or not but will not check for empty
    @NotEmpty //checks for both null and empty
    @Size(min = 4, message = "Username must be mininmum of 4 charcters")
    private String name;
    @Email(message = "your email address is not valid") //valid email must be given
    private String email;
    @NotEmpty
    @Size(min = 3, max = 10, message = "password must be min of 3 chars and max of 10 chars")
//    @Pattern(regexp = "")//this we can pass patterns and give regular expression and validate
    private String password;
    @NotEmpty
    private String about;
}
