package com.codemine.blog_app_apis.payloads;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private Integer categoryId;

    @NotBlank(message = "title not blank")
    private String categoryTitle;

    @NotBlank(message = "desc not blank") // String so I gave @NotBlank
    private String categoryDescription;

    /*
    difference between @NotNull @NotEmpty @NotBlank
    @NotNull -> work on any type, only check for null, allows "" and " "
    @NotEmpty-> works on String and Collections, checks null and "", allows " "
    @NotBlank-> works on only Strings, checks for null and " " and ""
     */
}
