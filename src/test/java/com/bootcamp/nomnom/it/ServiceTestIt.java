package com.bootcamp.nomnom.it;

import com.bootcamp.nomnom.TestData;
import com.bootcamp.nomnom.entity.Comment;
import com.bootcamp.nomnom.entity.Like;
import com.bootcamp.nomnom.entity.Recipe;
import com.bootcamp.nomnom.entity.User;
import com.bootcamp.nomnom.repository.UserRepository;
import com.bootcamp.nomnom.service.CommentService;
import com.bootcamp.nomnom.service.LikeService;
import com.bootcamp.nomnom.service.RecipeService;
import com.bootcamp.nomnom.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.verify;

// Here we create some simple integration tests testing random things because we promised Valdis.

@SpringBootTest
public class ServiceTestIt {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RecipeService recipeService;

    @Autowired
    CommentService commentService;

    @Autowired
    LikeService likeService;

    User user;
    Recipe recipe;
    Like like;
    Comment comment;
    MultipartFile multipartFile;

    private final String TEST_FILENAME_2 = "testFilename_2.png";
    private final String ABSOLUTE_PATH = "./src/main/resources/static/images/recipe/";
    private final String TEST_CONTENT = "This is test content";

    @BeforeEach
    public void setUp(){
        user = TestData.getUser();
        recipe = TestData.getRecipe();
        like = TestData.getLike();
        comment = TestData.getComment();
    }

    @Test
    public void retrieveUser(){
        UserDetails userTest = userService.loadUserByUsername(TestData.TEST_USERNAME);

        assertEquals(TestData.TEST_USERNAME, userTest.getUsername());
        assertNotEquals(TestData.TEST_PASSWORD,userTest.getPassword());
    }

    @Test
    public void retrieveRecipe() throws IOException {
        Recipe retrievedRecipe = recipeService.getRecipeById(TestData.TEST_ID);

        assertEquals(TestData.TEST_FILENAME, retrievedRecipe.getFileName());
        assertEquals(TestData.TEST_RECIPE_BODY, retrievedRecipe.getRecipeBody());
        assertEquals( TestData.TEST_TITLE, retrievedRecipe.getTitle());
    }

}
