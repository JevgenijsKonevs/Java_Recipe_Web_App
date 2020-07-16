package com.bootcamp.nomnom.service;

import com.bootcamp.nomnom.entity.Comment;
import com.bootcamp.nomnom.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;

    @InjectMocks
    CommentService commentService;

    private Comment comment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        comment = ServiceTestData.getComment();
    }

    @Test
    void saveCommentTest() {
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        assertEquals(comment, commentService.saveComment(comment));
    }

    @Test
    void deleteCommentTest() {
        when(commentRepository.findById(any(Long.class))).thenReturn(Optional.of(comment));

        commentService.deleteComment(ServiceTestData.TEST_ID);
        verify(commentRepository, atLeastOnce()).findById(any(Long.class));
        verify(commentRepository, atLeastOnce()).delete(any(Comment.class));
    }

    @Test
    void deleteCommentTestFails() {
        assertThrows(EntityNotFoundException.class, () -> {
            commentService.deleteComment(ServiceTestData.TEST_ID);
        });
    }
}
