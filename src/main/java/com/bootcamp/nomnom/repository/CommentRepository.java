package com.bootcamp.nomnom.repository;

import com.bootcamp.nomnom.entity.Comment;
import com.bootcamp.nomnom.entity.Recipe;
import com.bootcamp.nomnom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
