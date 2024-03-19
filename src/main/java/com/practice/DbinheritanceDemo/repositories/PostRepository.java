package com.practice.DbinheritanceDemo.repositories;

import com.practice.DbinheritanceDemo.models.Category;
import com.practice.DbinheritanceDemo.models.Post;
import com.practice.DbinheritanceDemo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByCategory(Category category);

    List<Post> findByUser(User user);
//    @Query("select p from Post p where p.title like concat('%', ?1, '%')")
    List<Post> findByTitleContaining(String Title);
}