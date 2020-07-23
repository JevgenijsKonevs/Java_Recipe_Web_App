package com.bootcamp.nomnom.controller;

import com.bootcamp.nomnom.TestData;
import com.bootcamp.nomnom.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomErrorControllerTest {

    @InjectMocks
    CustomErrorController customErrorController;

    Model model;
    User user;

    @BeforeEach
    void setUp() {
        user = TestData.getUser();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loginPageTest(){
        assertEquals("sad-cupcake-error", customErrorController.handleError(user, model));
    }

    @Test
    public void errorPathTest(){
        assertEquals("/error", customErrorController.getErrorPath());

    }

}
