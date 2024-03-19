package com.practice.DbinheritanceDemo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;
@Entity
@Table(name="categories")
@NoArgsConstructor
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;
    @Column(name="title",length = 100,nullable = false)

    @NotBlank
    @Size(min=4,message = "the min size of title is 4")
    private String categoryTitle;
    @Column(name="description")
    @Size(min=10,message = "the min size of description is 10")
    private String categoryDescription;
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch =FetchType.LAZY)
    private List<Post> posts=new ArrayList<>();


}
