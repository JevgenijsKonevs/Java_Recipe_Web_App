package com.bootcamp.nomnom.service;

import com.bootcamp.nomnom.TestData;
import com.bootcamp.nomnom.entity.Comment;
import com.bootcamp.nomnom.entity.Like;
import com.bootcamp.nomnom.entity.Recipe;
import com.bootcamp.nomnom.entity.User;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    private final String TEST_FILENAME_2 = "testFilename_2.png";

    private Recipe recipe;
    private Like like;
    private Comment comment;
    private MultipartFile multipartFile;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        recipe = TestData.getRecipe();
        like = TestData.getLike();
        comment = TestData.getComment();
        user = TestData.getUser();
    }

    @Test
    void getRecipeByIdTest() {
        when(recipeRepository.findById(any(Long.class))).thenReturn(Optional.of(recipe));
        assertEquals(recipe, recipeService.getRecipeById(TestData.TEST_ID));
    }

    @Test
    void getRecipeByIdTestFails() {
        assertThrows(EntityNotFoundException.class, () -> {
            recipeService.getRecipeById(TestData.TEST_ID);
        });
    }

    @Test
    void getAllRecipeByUserTest() {
        when(recipeRepository.findByUser_Id(any(Long.class))).thenReturn(new HashSet<>(Collections.singletonList(recipe)));
        Set<Recipe> actualRecipes = recipeService.getAllRecipeByUser(TestData.TEST_ID);
        assertEquals(1, actualRecipes.size());
        assertTrue(actualRecipes.contains(recipe));
    }

    @Test
    void listAllTest() {
        Page<Recipe> p = new PageImpl<>(Collections.singletonList(recipe));
        when(recipeRepository.findAll(any(PageRequest.class))).thenReturn(p);

        assertEquals(p, recipeService.listAll(1));
    }

    @Test
    void saveRecipeNoImageTest() throws IOException {
        multipartFile = null;

        Recipe retrieved = recipeService.saveRecipe(recipe, user, multipartFile);

        assertEquals(recipe.getTitle(), retrieved.getTitle());
        assertEquals(recipe.getRecipeBody(), retrieved.getRecipeBody());
        assertEquals("default.png", retrieved.getFileName());
    }

    @Test
    void saveRecipeHasImageTest() throws IOException {
        multipartFile = TestData.getMockMultipartFile(TestData.TEST_CONTENT);
        recipe.setFileName(TEST_FILENAME_2);

        Files.write(Paths.get(TestData.RECIPE_ABSOLUTE_PATH + recipe.getFileName()), multipartFile.getBytes());
        assertTrue(Files.exists(Paths.get(TestData.RECIPE_ABSOLUTE_PATH + recipe.getFileName())));

        Recipe retrieved = recipeService.saveRecipe(recipe, user, multipartFile);

        assertEquals(recipe.getTitle(), retrieved.getTitle());
        assertEquals(recipe.getRecipeBody(), retrieved.getRecipeBody());

        assertTrue(Files.exists(Paths.get(TestData.RECIPE_ABSOLUTE_PATH + recipe.getFileName())));

        Files.delete(Paths.get(TestData.RECIPE_ABSOLUTE_PATH + recipe.getFileName()));
        assertFalse(Files.exists(Paths.get(TestData.RECIPE_ABSOLUTE_PATH + recipe.getFileName())));
    }

    @Test
    void updateRecipeTest() {
        when(recipeRepository.findById(any(Long.class))).thenReturn(Optional.of(recipe));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
        assertEquals(recipe, recipeService.updateRecipeWithoutImages(recipe, user));
    }

    @Test
    void updateRecipePictureOnEmptyFileTest() {
        multipartFile = TestData.getMockMultipartFile("");
        assertEquals(recipe, recipeService.updateRecipePicture(recipe, multipartFile));
    }

    @Test
    void updateRecipePictureTest() throws IOException {
        multipartFile = TestData.getMockMultipartFile(TestData.TEST_CONTENT);
        recipe.setFileName(TEST_FILENAME_2);

        Files.write(Paths.get(TestData.RECIPE_ABSOLUTE_PATH + recipe.getFileName()), multipartFile.getBytes());
        assertTrue(Files.exists(Paths.get(TestData.RECIPE_ABSOLUTE_PATH + recipe.getFileName())));

        recipeService.updateRecipePicture(recipe, multipartFile);

        assertFalse(Files.exists(Paths.get(TestData.RECIPE_ABSOLUTE_PATH + TEST_FILENAME_2)));
        assertTrue(Files.exists(Paths.get(TestData.RECIPE_ABSOLUTE_PATH + recipe.getFileName())));
        assertNotEquals(TEST_FILENAME_2, recipeService.updateRecipePicture(recipe, multipartFile).getFileName());

        Files.delete(Paths.get(TestData.RECIPE_ABSOLUTE_PATH + recipe.getFileName()));
        assertFalse(Files.exists(Paths.get(TestData.RECIPE_ABSOLUTE_PATH + recipe.getFileName())));
    }

    @Test
    void deleteRecipePictureTest() throws IOException {
        multipartFile = TestData.getMockMultipartFile(TestData.TEST_CONTENT);
        recipe.setFileName(TestData.TEST_FILENAME);

        Files.write(Paths.get(TestData.RECIPE_ABSOLUTE_PATH + TestData.TEST_FILENAME), multipartFile.getBytes());

        assertTrue(Files.exists(Paths.get(TestData.RECIPE_ABSOLUTE_PATH + TestData.TEST_FILENAME)));
        assertEquals("default.png", recipeService.deleteRecipePicture(recipe).getFileName());
        assertFalse(Files.exists(Paths.get(TestData.RECIPE_ABSOLUTE_PATH + TestData.TEST_FILENAME)));
    }

    @Test
    void deleteRecipeTest() {
        when(recipeRepository.findById(any(Long.class))).thenReturn(Optional.of(recipe));
        when(likeRepository.findByRecipe_Id(any(Long.class))).thenReturn(new HashSet<>(Collections.singletonList(like)));
        when(commentRepository.findByRecipe_Id(any(Long.class))).thenReturn(new HashSet<>(Collections.singletonList(comment)));
        when(recipeRepository.findById(any(Long.class))).thenReturn(Optional.of(recipe));

        recipeService.deleteRecipeById(TestData.TEST_ID);
        verify(likeRepository, atLeastOnce()).delete(any(Like.class));
        verify(commentRepository, atLeastOnce()).delete(any(Comment.class));
        verify(recipeRepository, atLeastOnce()).delete(any(Recipe.class));
    }

    @Test
    void deleteRecipeTestFails() {
        assertThrows(EntityNotFoundException.class, () -> {
            recipeService.deleteRecipeById(recipe.getId());
        });
    }

    @Test
    void hasRatedTestReturnsTRUE() {
        when(likeRepository.findByRecipe_Id(anyLong())).thenReturn(TestData.getSetOfLikes(1, 0));

        assertTrue(recipeService.hasRated(TestData.TEST_ID, TestData.TEST_ID));
    }

    @Test
    void hasRatedTestReturnsFALSE() {
        when(likeRepository.findByRecipe_Id(anyLong())).thenReturn(TestData.getSetOfLikes(1, 0));

        assertFalse(recipeService.hasRated(999L, TestData.TEST_ID));
    }

    @Test
    void getAllCommentsTest() {
        when(commentRepository.findByRecipe_Id(any(Long.class))).thenReturn(new HashSet<>(Collections.singletonList(comment)));

        Set<Comment> actualComments = recipeService.getAllComments(comment.getId());
        verify(commentRepository, atLeastOnce()).findByRecipe_Id(any(Long.class));
        assertEquals(1, actualComments.size());
        assertTrue(actualComments.contains(comment));
    }

    @Test
    void getAllLikesTest() {
        when(likeRepository.findByRecipe_Id(any(Long.class))).thenReturn(new HashSet<>(Collections.singletonList(like)));

        Set<Like> actualComments = recipeService.getAllLikes(TestData.TEST_ID);
        verify(likeRepository, atLeastOnce()).findByRecipe_Id(any(Long.class));
        assertEquals(1, actualComments.size());
        assertTrue(actualComments.contains(like));
    }

    @Test
    void searchRecipeTest() {
        Page<Recipe> p = new PageImpl<>(Collections.singletonList(TestData.getRecipe()));
        when(recipeRepository.findByTitleContainingIgnoreCase(anyString(), any(Pageable.class))).thenReturn(p);

        assertEquals(p, recipeService.searchRecipe("keyword", 2));
    }

    @Test
    void previewRecipeListTest() {
        when(recipeRepository.findById(any(Long.class))).thenReturn(Optional.of(new Recipe()));

        assertEquals(4, recipeService.previewRecipeList().size());
    }
}
