package com.codemine.blog_app_apis.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String content;

    //on which post the comment is made
    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;
}
