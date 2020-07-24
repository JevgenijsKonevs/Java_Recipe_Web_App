package com.bootcamp.nomnom.controller;

import com.bootcamp.nomnom.TestData;
import com.bootcamp.nomnom.entity.Comment;
import com.bootcamp.nomnom.entity.Like;
import com.bootcamp.nomnom.entity.Recipe;
import com.bootcamp.nomnom.entity.User;
import com.bootcamp.nomnom.service.CommentService;
import com.bootcamp.nomnom.service.LikeService;
import com.bootcamp.nomnom.service.RecipeService;
import com.bootcamp.nomnom.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class RecipeControllerTest {

    @InjectMocks
    RecipeController recipeController;

    @Mock
    UserService userService;

    @Mock
    RecipeService recipeService;

    @Mock
    CommentService commentService;

    @Mock
    LikeService likeService;

    @Mock
    Model model;

    User user;
    MultipartFile multipartFile;
    Recipe recipe;
    Comment comment;
    Like like;

    @BeforeEach
    void setUp() {
        user = TestData.getUser();
        recipe = TestData.getRecipe();
        comment = TestData.getComment();
        like = TestData.getLike();
        multipartFile = TestData.getMockMultipartFile("test");
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllRecipesTest() {
        assertEquals("redirect:/recipe/page/1", recipeController.getAllRecipes(model));
    }

    @Test
    public void getRecipesOnPageTest() {
        Page<Recipe> page = Page.empty();
        List<Recipe> recipeList = Collections.emptyList();
        when(recipeService.listAll(anyInt())).thenReturn(page);
        assertEquals("recipes", recipeController.getRecipesOnPage(model, 1, user));
    }

    @Test
    public void searchRecipesTest() {
        Page<Recipe> page = Page.empty();
        when(recipeService.searchRecipe(anyString(), anyInt())).thenReturn(page);
        assertEquals("search-results", recipeController.searchRecipes(model, "keyword", 1, user));
    }

    @Test
    public void recipePageTest() {
        when(recipeService.getRecipeById(anyLong())).thenReturn(recipe);
        when(recipeService.getAllComments(anyLong())).thenReturn(Collections.singleton(comment));
        when(recipeService.getAllLikes(anyLong())).thenReturn(Collections.singleton(like));
        when(likeService.getRecipeLikes(anyLong())).thenReturn(1L);
        when(likeService.getRecipeDislikes(anyLong())).thenReturn(1L);

        assertEquals("recipe", recipeController.recipePage(TestData.TEST_ID, model, user));

    }

    @Test
    public void createRecipeTest() {
        assertEquals("new-recipe", recipeController.createRecipe(user, model));
    }

    @Test
    public void postRecipeTest() throws IOException {
        when(recipeService.saveRecipe(any(Recipe.class), any(User.class), any(MultipartFile.class))).thenReturn(recipe);
        assertEquals("redirect:/recipe/", recipeController.postRecipe(recipe, user, multipartFile));
    }

    @Test
    public void editRecipeTest() {
        when(recipeService.getRecipeById(anyLong())).thenReturn(recipe);
        assertEquals("edit-recipe", recipeController.editRecipe(model, TestData.TEST_ID, user));
    }

    @Test
    public void updateRecipeHasImage() throws IOException {
        when(recipeService.getRecipeById(anyLong())).thenReturn(recipe);
        when(recipeService.saveRecipe(any(Recipe.class), any(User.class), any(MultipartFile.class))).thenReturn(recipe);
        when(recipeService.updateRecipeWithoutImages(any(Recipe.class), any(User.class))).thenReturn(recipe);

        assertEquals("redirect:/recipe/" + TestData.TEST_ID, recipeController.updateRecipe(TestData.TEST_ID, recipe, user, multipartFile, "update"));
    }

    @Test
    public void updateRecipeDeleteImage() throws IOException {
        when(recipeService.getRecipeById(anyLong())).thenReturn(recipe);
        when(recipeService.saveRecipe(any(Recipe.class), any(User.class), any(MultipartFile.class))).thenReturn(recipe);
        when(recipeService.deleteRecipePicture(recipe)).thenReturn(recipe);

        assertEquals("redirect:/recipe/" + TestData.TEST_ID, recipeController.updateRecipe(TestData.TEST_ID, recipe, user, multipartFile, "noUpdate"));
    }

    @Test
    public void deleteRecipeTest() {
        when(recipeService.getRecipeById(anyLong())).thenReturn(recipe);
        assertEquals("redirect:/recipe/", recipeController.deleteRecipe(TestData.TEST_ID, user));
    }

    @Test
    public void addCommentUserNullTest() {
        user = null;
        assertEquals("redirect:/login", recipeController.addComment(TestData.TEST_ID, comment, user));
    }

    @Test
    public void addCommentUserNotNullTest() {
        when(recipeService.getRecipeById(anyLong())).thenReturn(recipe);
        when(commentService.saveComment(comment)).thenReturn(comment);
        assertEquals("redirect:/recipe/" + TestData.TEST_ID, recipeController.addComment(TestData.TEST_ID, comment, user));
    }

    @Test
    public void deleteCommentTest() {
        when(commentService.getCommentById(anyLong())).thenReturn(comment);
        assertEquals("redirect:/recipe/" + TestData.TEST_ID, recipeController.deleteComment(TestData.TEST_ID, TestData.TEST_ID, user));
    }

    @Test
    public void addLikeUserNullTest() {
        user = null;
        assertEquals("redirect:/login", recipeController.submitLike(TestData.TEST_ID, "like", user));
    }

    @Test
    public void addLikeUserIsNullTest() {
        when(recipeService.getRecipeById(anyLong())).thenReturn(recipe);
        when(recipeService.hasRated(anyLong(), anyLong())).thenReturn(false);
        assertEquals("redirect:/recipe/" + TestData.TEST_ID, recipeController.submitLike(TestData.TEST_ID, "like", user));
    }
}
