package com.bootcamp.nomnom.controller;

import com.bootcamp.nomnom.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomeControllerTest {

    @InjectMocks
    HomeController homeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

//    @Test
//    public void homePageTest(){
//        assertEquals("home", homeController.homePage());
//    }
}
