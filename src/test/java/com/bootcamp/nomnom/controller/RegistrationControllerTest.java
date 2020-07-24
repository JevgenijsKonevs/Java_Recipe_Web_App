package com.bootcamp.nomnom.controller;

import com.bootcamp.nomnom.TestData;
import com.bootcamp.nomnom.entity.User;
import com.bootcamp.nomnom.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class RegistrationControllerTest {

    @InjectMocks
    RegistrationController registrationController;

    @Mock
    UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = TestData.getUser();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void registerPageTest() {
        assertEquals("register", registrationController.registerPage());
    }

    @Test
    public void addUserThatDoesNotExistTest() {
        when(userService.userExists(anyString())).thenReturn(false);
        when(userService.saveUserRegister(user)).thenReturn(user);

        assertEquals("redirect:/login", registrationController.addUser(user));
    }

    @Test
    public void addUserThatAlreadyExistsTest() {
        when(userService.userExists(anyString())).thenReturn(true);

        assertEquals("redirect:/register", registrationController.addUser(user));
    }
}
