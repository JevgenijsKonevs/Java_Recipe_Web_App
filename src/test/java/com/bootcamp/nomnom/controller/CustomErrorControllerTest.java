package com.bootcamp.nomnom.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomErrorControllerTest {

    @InjectMocks
    CustomErrorController customErrorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loginPageTest(){
        assertEquals(customErrorController.handleError(), "sad-cupcake-error");
    }

    @Test
    public void errorPathTest(){
        assertEquals(customErrorController.getErrorPath(), "/error");

    }

}
