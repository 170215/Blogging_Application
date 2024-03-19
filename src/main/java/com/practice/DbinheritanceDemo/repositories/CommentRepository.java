package com.practice.DbinheritanceDemo.repositories;

import com.practice.DbinheritanceDemo.models.Comment;
import com.practice.DbinheritanceDemo.models.Post;
import com.practice.DbinheritanceDemo.models.User;
import com.practice.DbinheritanceDemo.payloads.CommentDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
   List<Comment> findByUser(User user);
   List<Comment> findByPost(Post post);
}