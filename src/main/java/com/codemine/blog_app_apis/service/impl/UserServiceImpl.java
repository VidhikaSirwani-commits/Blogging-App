package com.codemine.blog_app_apis.service.impl;

import com.codemine.blog_app_apis.entities.User;
import com.codemine.blog_app_apis.exceptions.ResourceNotFoundException;
import com.codemine.blog_app_apis.payloads.UserDto;
import com.codemine.blog_app_apis.repository.UserRepo;
import com.codemine.blog_app_apis.service.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        /*
        Always in service we do not expose our entities there so we will work with the dtos
        1. we are getting a dto from the controller. but the repo it works with the entity objects only
        2. so for repo to save the user we will convert that dto to entity
        3. now that user we will save to the repo
        4. once user saved again have to send to controller, there we send in form of dto so we that entity is not exposed
           for this reason only we will coner the entity to dto again
        5. now the dto is sent to controller back and then the client gets this as the reponse
         */
        User user= this.dtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }
/*
while updating the user we will do following
1. find the user based on id and then work on that user to update with the new desired values
2. note we will throw the exception also if the user is not found then

 */
    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        //first get the user based on userId so that based on that user we can update
        User user=this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));

        // now this user we will update the new values here
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
// saving the updated user to repo
        User updatedUser = this.userRepo.save(user);

        //now have to convert to dto because cannot expose the entity
        return this.userToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {

        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
        // now we have user but need to convert to dto as we cannot expose it
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepo.findAll();
        //now we have to send list of all the dtos so need to convert this users to dto
        List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        // first get the user then delete it
        User user=this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));

        // we can delete the whole user object now
        this.userRepo.delete(user);

// this is deleting the user based on id
//        this.userRepo.deleteById(userId);

    }

    private User dtoToUser(UserDto userDto){
        // lets convert the dto to user using object mapper
        /*
        1. for object mapper first create a bean of it
        2. we will inject that in our service
        3. we will use map() to map from one object to another object
         */
        User user= this.modelMapper.map(userDto, User.class);


        //this is the coversion without model mapper
//        User user = new User();
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());
        return user;
    }

    private UserDto userToDto(User user){

        UserDto userDto= this.modelMapper.map(user, UserDto.class);

//        UserDto userDto=new UserDto();
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setAbout(user.getAbout());
        return userDto;
    }
}
