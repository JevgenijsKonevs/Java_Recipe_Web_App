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
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
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
        when(likeRepository.findById(anyLong())).thenReturn(Optional.of(like));

        likeService.deleteLikeById(TestData.TEST_ID);
        verify(likeRepository, atLeastOnce()).findById(anyLong());
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

    @Test
    void getRecipeLikes() {
        Set<Like> likes = TestData.getSetOfLikes(10, 4);
        when(likeRepository.findByRecipe_Id(anyLong())).thenReturn(likes);

        assertEquals(6, likeService.getRecipeLikes(TestData.TEST_ID));
    }

    @Test
    void getRecipeDislikesTest() {
        Set<Like> likes = TestData.getSetOfLikes(10, 4);
        when(likeRepository.findByRecipe_Id(anyLong())).thenReturn(likes);

        assertEquals(4, likeService.getRecipeDislikes(TestData.TEST_ID));
    }

    @Test
    void getLikeByIdsTest() {
        when(likeRepository.findByRecipe_Id(anyLong())).thenReturn(Collections.singleton(like));

        assertEquals(like, likeService.getLikeByIds(TestData.TEST_ID, 10L));
    }

    @Test
    void getLikeByIdsTestReturnsNull() {
        when(likeRepository.findByRecipe_Id(anyLong())).thenReturn(Collections.singleton(like));

        assertNull(likeService.getLikeByIds(999L, 10L));
    }
}
