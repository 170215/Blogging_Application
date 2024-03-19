package com.practice.DbinheritanceDemo.services;

import com.practice.DbinheritanceDemo.exceptions.ResourceNotFoundException;
import com.practice.DbinheritanceDemo.models.Comment;
import com.practice.DbinheritanceDemo.models.Post;
import com.practice.DbinheritanceDemo.models.User;
import com.practice.DbinheritanceDemo.payloads.CommentDto;
import com.practice.DbinheritanceDemo.repositories.CommentRepository;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class CommentServiceImpl implements CommentService {

    CommentRepository commentRepository;
    ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(CommentDto commentDto) {
        Comment  comment =modelMapper.map(commentDto,Comment.class);
        comment=commentRepository.save(comment);
        return modelMapper.map(comment,CommentDto.class);
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, Integer id) throws ResourceNotFoundException {
        Comment comment=commentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Comment","comment_id",id));
        comment.setContent(commentDto.getContent());
        return modelMapper.map(comment,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer id) throws ResourceNotFoundException {
        Comment comment=commentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Comment","comment_id",id));
        commentRepository.delete(comment);

    }

    @Override
    public List<CommentDto> getAllCommentsByUser(User user) {
        List<Comment>comments=commentRepository.findByUser(user);
        List<CommentDto> commentDtos=comments.stream().map((comment)->modelMapper.map(comment,CommentDto.class)).collect(Collectors.toList());
        return commentDtos;
    }

    @Override
    public List<CommentDto> getAllCommentsByPost(Post post) {
        return null;
    }

    @Override
    public List<CommentDto> getAllComments() {
        return null;
    }
}
