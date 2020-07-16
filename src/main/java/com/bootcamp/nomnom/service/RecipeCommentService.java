package com.bootcamp.nomnom.service;

import com.bootcamp.nomnom.entity.Comment;
import com.bootcamp.nomnom.entity.Recipe;
import com.bootcamp.nomnom.entity.User;
import com.bootcamp.nomnom.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class RecipeCommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment saveCommentRegister(Comment comment) {
        commentRepository.save(comment);
        return comment;
    }

    public String deleteCommentRegister(Long id) {
        Comment commentToDelete = commentRepository.getOne(id);
        commentRepository.delete(commentToDelete);
        return "Comment deleted";
    }

}
