package com.bootcamp.nomnom.service;

import com.bootcamp.nomnom.entity.Comment;
import com.bootcamp.nomnom.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment saveComment(Comment comment) {
        commentRepository.save(comment);
        return comment;
    }

    public void deleteComment(Long id) {
        Comment commentToDelete = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id.toString()));
        commentRepository.delete(commentToDelete);
    }

    public Comment getCommentById(Long id){
        return commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id.toString()));
    }

}
