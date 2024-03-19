package com.practice.DbinheritanceDemo.payloads;

import com.practice.DbinheritanceDemo.models.Category;
import com.practice.DbinheritanceDemo.models.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    private Integer postid;

    private String title;
    private String content;
    private String imageName;
    private Date addedDate;
    private CategoryDto category;
    private UserDto user;

}
