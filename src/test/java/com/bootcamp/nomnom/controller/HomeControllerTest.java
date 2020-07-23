package com.bootcamp.nomnom.controller;

import com.bootcamp.nomnom.TestData;
import com.bootcamp.nomnom.entity.Recipe;
import com.bootcamp.nomnom.entity.User;
import com.bootcamp.nomnom.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class HomeControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    @InjectMocks
    HomeController homeController;

    User user;

    @BeforeEach
    void setUp() {
        user = TestData.getUser();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void homePageTest(){
        List<Recipe> emptyList = Collections.emptyList();
        when(recipeService.previewRecipeList()).thenReturn(emptyList);
        assertEquals("home", homeController.homePage(model, user));
    }
}
