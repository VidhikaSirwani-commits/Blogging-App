package com.codemine.blog_app_apis.controllers;

import com.codemine.blog_app_apis.payloads.ApiResponse;
import com.codemine.blog_app_apis.payloads.UserDto;
import com.codemine.blog_app_apis.payloads.UserResponse;
import com.codemine.blog_app_apis.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

//RestController is a combination of @Controller and @ResponseBody
//so this @ResponseBody is used to convert the java object onto json form and send back to client
@RestController
@RequestMapping("/apis/users")
public class UserController {

    @Autowired
    private UserService userService;

    // POST- create user
    @PostMapping
    // RequestBody used to convert the json we are getting into a java object
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto createUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }

    // PUT- update user
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable(name = "id") Integer userId){
        UserDto updatedUser= this.userService.updateUser(userDto, userId);
        //can give in this way also
        return ResponseEntity.ok(updatedUser);
//        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    //GET- find user
    @GetMapping
    public ResponseEntity<UserDto> getUser(@RequestParam Integer userId){
        UserDto getUserDto= this.userService.getUserById(userId);
        return new ResponseEntity<UserDto>(getUserDto, HttpStatus.OK);
    }

    //GET- find all users
    @GetMapping("/allUsers")
    public ResponseEntity<UserResponse> findUsers(
            @RequestParam(value = "pageNumber", defaultValue = "0",required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) Integer pageSize
    ){
//        List<UserDto> list= this.userService.getAllUsers();
//        return new ResponseEntity<List<UserDto>>(list, HttpStatus.OK);

        //can also make it in one line of code
        return new ResponseEntity<UserResponse>(this.userService.getAllUsers(pageNumber,pageSize), HttpStatus.OK);
    }

    // DELETE- delete user
    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteUser(@RequestParam Integer userId){
        this.userService.deleteUser(userId);
        // delete is not returning anything so if we want customized message and all can use this
//        return new ResponseEntity<>(Map.of("message","the user does not exists"), HttpStatus.OK);

        /*
         usually can create a ApiResponse class to handle a generic way of Response Entity should look like
         so usually they create an ApiResponse ntity class and then use it when returning a code
         */
        //now passing ApiResponse in our ResponseEntity rather than passing the map
        return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted successfully",true), HttpStatus.OK);
    }
}
