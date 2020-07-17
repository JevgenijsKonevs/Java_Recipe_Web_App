package com.bootcamp.nomnom.service;

import com.bootcamp.nomnom.entity.Comment;
import com.bootcamp.nomnom.entity.Like;
import com.bootcamp.nomnom.entity.Recipe;
import com.bootcamp.nomnom.repository.CommentRepository;
import com.bootcamp.nomnom.repository.LikeRepository;
import com.bootcamp.nomnom.repository.RecipeRepository;
import com.bootcamp.nomnom.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RecipeServiceTest {

    @Mock
    RecipeRepository recipeRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    CommentRepository commentRepository;
    @Mock
    LikeRepository likeRepository;

    @InjectMocks
    RecipeService recipeService;

    private Recipe recipe;
    private Like like;
    private Comment comment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        recipe = ServiceTestData.getRecipe();
        like = ServiceTestData.getLike();
        comment = ServiceTestData.getComment();
    }

    @Test
    void getRecipeByIdTest() {
        when(recipeRepository.findById(any(Long.class))).thenReturn(Optional.of(recipe));
        assertEquals(recipe, recipeService.getRecipeById(ServiceTestData.TEST_ID));
    }

    @Test
    void getRecipeByIdTestFails() {
        assertThrows(EntityNotFoundException.class, () -> {
            recipeService.getRecipeById(ServiceTestData.TEST_ID);
        });
    }

    @Test
    void getAllRecipeByUserTest() {
        when(recipeRepository.findByUser_Id(any(Long.class))).thenReturn(new HashSet<>(Collections.singletonList(recipe)));
        Set<Recipe> actualRecipes = recipeService.getAllRecipeByUser(ServiceTestData.TEST_ID);
        assertEquals(1, actualRecipes.size());
        assertTrue(actualRecipes.contains(recipe));
    }

    // TODO: Test will be written after method will be implemented
    @Disabled
    @Test
    void saveRecipeTest() {}

    @Test
    void updateRecipeTest() {
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
        assertEquals(recipe, recipeService.updateRecipe(recipe));
    }

    @Test
    void deleteRecipeTest() {
        when(likeRepository.findByRecipe_Id(any(Long.class))).thenReturn(new HashSet<>(Collections.singletonList(like)));
        when(commentRepository.findByRecipe_Id(any(Long.class))).thenReturn(new HashSet<>(Collections.singletonList(comment)));

        recipeService.deleteRecipe(recipe);
        verify(likeRepository, atLeastOnce()).delete(any(Like.class));
        verify(commentRepository, atLeastOnce()).delete(any(Comment.class));
        verify(recipeRepository, atLeastOnce()).delete(any(Recipe.class));
    }

    @Test
    void getAllCommentsTest() {
        when(commentRepository.findByRecipe_Id(any(Long.class))).thenReturn(new HashSet<>(Collections.singletonList(comment)));

        Set<Comment> actualComments = recipeService.getAllComments(ServiceTestData.TEST_ID);
        verify(commentRepository, atLeastOnce()).findByRecipe_Id(any(Long.class));
        assertEquals(1, actualComments.size());
        assertTrue(actualComments.contains(comment));
    }

    @Test
    void getAllLikesTest() {
        when(likeRepository.findByRecipe_Id(any(Long.class))).thenReturn(new HashSet<>(Collections.singletonList(like)));

        Set<Like> actualComments = recipeService.getAllLikes(ServiceTestData.TEST_ID);
        verify(likeRepository, atLeastOnce()).findByRecipe_Id(any(Long.class));
        assertEquals(1, actualComments.size());
        assertTrue(actualComments.contains(like));
    }
}
