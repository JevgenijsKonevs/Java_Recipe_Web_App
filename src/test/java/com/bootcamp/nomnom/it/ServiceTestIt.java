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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

// Here we create some simple integration tests testing random things because we promised Valdis.

@SpringBootTest
@ActiveProfiles("test")
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
    public void setUp() {
        user = TestData.getUser();
        recipe = TestData.getRecipe();
        like = TestData.getLike();
        comment = TestData.getComment();
    }

    @Test
    public void retrieveUserTest() {
        UserDetails userTest = userService.loadUserByUsername(TestData.TEST_USERNAME);

        assertEquals(TestData.TEST_USERNAME, userTest.getUsername());
        assertNotEquals(TestData.TEST_PASSWORD, userTest.getPassword());
    }

    @Test
    public void retrieveUserRecipesTest() {
        Set<Recipe> retrievedRecipes = recipeService.getAllRecipeByUser(1L);
        Recipe testRecipe = null;

        for (Recipe recipe : retrievedRecipes) {
            testRecipe = recipe;
        }

        assertEquals(TestData.TEST_ID, testRecipe.getId());
        assertEquals(TestData.TEST_RECIPE_BODY, testRecipe.getRecipeBody());
        assertEquals(TestData.TEST_TITLE, testRecipe.getTitle());
    }

    @Test
    public void retrieveRecipeTest() throws IOException {
        Recipe retrievedRecipe = recipeService.getRecipeById(TestData.TEST_ID);

        assertEquals(TestData.TEST_FILENAME, retrievedRecipe.getFileName());
        assertEquals(TestData.TEST_RECIPE_BODY, retrievedRecipe.getRecipeBody());
        assertEquals(TestData.TEST_TITLE, retrievedRecipe.getTitle());
    }

    @Test
    public void retrieveRecipeCommentTest() {
        Recipe retrievedRecipe = recipeService.getRecipeById(TestData.TEST_ID);
        Set<Comment> retrievedComments = recipeService.getAllComments(retrievedRecipe.getId());
        Comment retrievedComment = null;
        for (Comment comment : retrievedComments) {
            retrievedComment = comment;
        }

        assertEquals(TestData.getComment().getId(), retrievedComment.getId());
        assertEquals(TestData.getComment().getRecipeComment(), retrievedComment.getRecipeComment());
    }

    @Test
    public void saveAndDeleteRecipeCommentTest() {
        Recipe retrievedRecipe = recipeService.getRecipeById(TestData.TEST_ID);
        Comment comment = new Comment();
        comment.setRecipe(retrievedRecipe);

        commentService.saveComment(comment);
        Set<Comment> allComents = recipeService.getAllComments(retrievedRecipe.getId());
        assertEquals(2, allComents.size());

        for (Comment commentTest : allComents) {
            if (commentTest.getId() != 1) {
                commentService.deleteComment(comment.getId());
            }
        }
        assertEquals(1, recipeService.getAllComments(1L).size());
    }

    @Test
    public void retrieveRecipeLikeTest() {
        Recipe retrievedRecipe = recipeService.getRecipeById(TestData.TEST_ID);
        Set<Like> retrievedLikes = recipeService.getAllLikes(retrievedRecipe.getId());
        Like retrievedLike = null;
        for (Like like : retrievedLikes) {
            retrievedLike = like;
        }

        assertEquals(TestData.getComment().getId(), retrievedLike.getId());
        assertEquals(TestData.getLike().getRecipeLike(), retrievedLike.getRecipeLike());
    }
}
