package com.bootcamp.nomnom.controller;

import com.bootcamp.nomnom.TestData;
import com.bootcamp.nomnom.entity.Like;
import com.bootcamp.nomnom.entity.User;
import com.bootcamp.nomnom.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    private User user;
    private Model model;
    private MultipartFile multipartFile;


    @BeforeEach
    void setUp() {
        user = TestData.getUser();
        multipartFile = TestData.getMockMultipartFile("test");
        MockitoAnnotations.initMocks(this);
    }

//    @Test
//    public void showProfilePageTest(){
//        assertEquals("profile",userController.showProfilePage(model));
//    }

    @Test
    public void findUserProfileSameUserTest(){
        assertEquals("profile",userController.findUserProfile(user.getUsername(), user));
    }

    @Test
    public void findUserProfileDifferentUserTest(){
        assertEquals("user-page",userController.findUserProfile("differentName", user));
    }

    @Test
    public void postImageTest() throws IOException {
        assertEquals("redirect:/user/profile", userController.postImage(multipartFile,user));
        verify(userService, atLeastOnce()).saveProfilePhoto(any(User.class), any(MultipartFile.class)) ;
    }

}
