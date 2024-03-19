package com.practice.DbinheritanceDemo.services;

import com.practice.DbinheritanceDemo.exceptions.ResourceNotFoundException;
import com.practice.DbinheritanceDemo.models.Post;
import com.practice.DbinheritanceDemo.models.User;
import com.practice.DbinheritanceDemo.payloads.CommentDto;

import java.util.List;

public interface CommentService {


    CommentDto createComment(CommentDto commentDto);
    CommentDto updateComment(CommentDto commentDto, Integer id) throws ResourceNotFoundException;

    void deleteComment(Integer id) throws ResourceNotFoundException;

    List<CommentDto> getAllCommentsByUser(User user);
    List<CommentDto> getAllCommentsByPost(Post post);

    List<CommentDto> getAllComments();
}
