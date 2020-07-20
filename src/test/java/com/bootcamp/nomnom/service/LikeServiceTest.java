package com.bootcamp.nomnom.service;

import com.bootcamp.nomnom.TestData;
import com.bootcamp.nomnom.entity.Like;
import com.bootcamp.nomnom.repository.LikeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LikeServiceTest {

    @Mock
    LikeRepository likeRepository;

    @InjectMocks
    LikeService likeService;

    private Like like;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        like = TestData.getLike();

        when(likeRepository.save(any(Like.class))).thenReturn(like);
    }

    @Test
    void saveLikeTest() {
        assertEquals(like, likeService.saveLike(like));
    }

    @Test
    void deleteLikeByIdTest() {
        when(likeRepository.findById(any(Long.class))).thenReturn(Optional.of(like));

        likeService.deleteLikeById(TestData.TEST_ID);
        verify(likeRepository, atLeastOnce()).findById(any(Long.class));
        verify(likeRepository, atLeastOnce()).delete(any(Like.class));
    }

    @Test
    void deleteLikeByIdTestFails() {
        assertThrows(EntityNotFoundException.class, () -> {
            likeService.deleteLikeById(TestData.TEST_ID);
        });
    }

    @Test
    void updateLikeTest() {
        assertEquals(like, likeService.updateLike(like));
    }
}
