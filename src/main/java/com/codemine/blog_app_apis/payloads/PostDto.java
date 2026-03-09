package com.codemine.blog_app_apis.payloads;

import com.codemine.blog_app_apis.entities.Category;
import com.codemine.blog_app_apis.entities.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    private Integer postId;
    // from user we will get only title and content
    private String title;

    private String content;
    /*
     the imageName we will take later
     the addedDate we will set later when we upload the post
     also we will set post belongs to which category and which user as well
     */

    // here we are adding imageName, user and category
    //this we will use when we want to return so that time to display we are adding these
//but real life we keep seperate payloads to take and recieve the data so no confusion will happen

    private String imageName;

    private Date addedDate;
    /*
    we are using ModelMapper so very important we keep the feild name same as the entity
    I had written previously
    private CategoryDto categoryDto;
    because of categoryDto the feild name changed it was not able to map and was getting null value
     */
    private CategoryDto category;

    private UserDto user;
}
