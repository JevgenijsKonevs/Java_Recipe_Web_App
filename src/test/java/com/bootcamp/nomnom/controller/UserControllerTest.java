package com.bootcamp.nomnom.controller;

import com.bootcamp.nomnom.TestData;
import com.bootcamp.nomnom.entity.Recipe;
import com.bootcamp.nomnom.entity.User;
import com.bootcamp.nomnom.service.RecipeService;
import com.bootcamp.nomnom.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    UserService userService;

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    @InjectMocks
    UserController userController;

    private User user;
    private MultipartFile multipartFile;


    @BeforeEach
    void setUp() {
        user = TestData.getUser();
        multipartFile = TestData.getMockMultipartFile("test");
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void showProfilePageTest() {
        Set<Recipe> emptySet = Collections.emptySet();
        when(recipeService.getAllRecipeByUser(anyLong())).thenReturn(emptySet);
        assertEquals("profile", userController.showProfilePage(model, user));
    }

    @Test
    public void findUserProfileSameUserTest() {
        when(userService.getUserByUsername(anyString())).thenReturn(user);
        assertEquals("redirect:/user/profile", userController.findUserProfile(model, user.getUsername(), user));
    }

    @Test
    public void findUserProfileDifferentUserTest() {
        Set<Recipe> emptySet = Collections.emptySet();
        when(recipeService.getAllRecipeByUser(anyLong())).thenReturn(emptySet);
        when(userService.getUserByUsername(anyString())).thenReturn(user);
        assertEquals("user-page", userController.findUserProfile(model, "differentName", user));
    }

    @Test
    public void postImageTest() throws IOException {
        assertEquals("redirect:/user/profile", userController.postImage(multipartFile, user, "update"));
        verify(userService, atLeastOnce()).saveProfilePhoto(any(User.class), any(MultipartFile.class));
    }

    @Test
    public void postImageDeleteTest() throws IOException {
        assertEquals("redirect:/user/profile", userController.postImage(multipartFile, user, "nope"));
        verify(userService, atLeastOnce()).deleteUserPicture(any(User.class));
    }

    @Test
    public void updatePasswordTest() {
        when(userService.updateUserPassword(any(User.class), anyString())).thenReturn(user);
        assertEquals("redirect:/user/profile", userController.changeUserPassword(user, "password"));
    }

}
