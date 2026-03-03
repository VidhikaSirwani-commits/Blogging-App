package com.codemine.blog_app_apis.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Integer categoryId;
    // now this column name in db will be title, length will be 100
    //also null values will not be accepted here
    @Column(name = "title", length = 100,nullable = false)
    private String categoryTitle;

    @Column(name = "description")
    private String categoryDescription;

}
