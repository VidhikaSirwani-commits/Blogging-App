package com.codemine.blog_app_apis.service;

import com.codemine.blog_app_apis.entities.User;
import com.codemine.blog_app_apis.payloads.UserDto;

import java.util.List;

public interface UserService {
    /* rather than passing a user directly i will create and pass a DTO object
    if i pass the entity in my service it is like i am exposing my db
    so to avoid this i will create a dto and pass only the feilds i require
    2. this will also optimize the performace because rather than fetching all the
    columns i am accessing only the columns i need
     */
    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto,Integer userId);

    UserDto getUserById(Integer userId);

    List<UserDto> getAllUsers();

    void deleteUser(Integer userId);
}
