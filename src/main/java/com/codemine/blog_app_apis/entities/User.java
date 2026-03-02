package com.codemine.blog_app_apis.entities;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User {
// will create id and generate the primary values automatically
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    // to change the column name and all can us @Column
    //nullable = false means no null values allowed
    @Column(name = "user_name", nullable = false, length = 100)
    private String name;
    private String email;
    private String password;
    private String about;
}
