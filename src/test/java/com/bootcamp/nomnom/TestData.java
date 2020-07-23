package com.bootcamp.nomnom;

import com.bootcamp.nomnom.entity.Comment;
import com.bootcamp.nomnom.entity.Like;
import com.bootcamp.nomnom.entity.Recipe;
import com.bootcamp.nomnom.entity.User;
import org.springframework.mock.web.MockMultipartFile;

import java.util.HashSet;
import java.util.Set;

public class TestData {

    public static final long TEST_ID = 1L;
    public static final String TEST_USERNAME = "TestUsername";
    public static final String TEST_PASSWORD = "TestPassword";
    public static final String TEST_FILE_NAME = "TestFileName";
    public static final String TEST_TITLE = "TestTitle";
    public static final String TEST_RECIPE_COMMENT = "Test recipe comment!";
    public static final String TEST_FILENAME = "testFilename.png";
    public static final String TEST_RECIPE_BODY = "TestRecipeBody";
    public static final String TEST_CONTENT = "This is test content";
    public static final String RECIPE_ABSOLUTE_PATH = "./src/main/uploads/images/recipe/";
    public static final String USER_ABSOLUTE_PATH = "./src/main/uploads/images/user/";

    public static User getUser() {
        User user = new User();
        user.setId(TEST_ID);
        user.setUsername(TEST_USERNAME);
        user.setPassword(TEST_PASSWORD);
        user.setFileName(TEST_FILENAME);
        return user;
    }

    public static Recipe getRecipe() {
        Recipe recipe = new Recipe();
        recipe.setId(TEST_ID);
        recipe.setFileName(TEST_FILE_NAME);
        recipe.setTitle(TEST_TITLE);
        recipe.setRecipeBody(TEST_RECIPE_BODY);
        recipe.setUser(getUser());
        return recipe;
    }

    public static Like getLike() {
        return getLike(true);
    }
    public static Like getLike(boolean isRecipeLike) {
        Like like = new Like();
        like.setId(TEST_ID);
        like.setRecipe(getRecipe());
        like.setUser(getUser());
        like.setRecipeLike(isRecipeLike);
        return like;
    }

    public static Set<Like> getSetOfLikes(int totalNumberOfLikes, int numberOfDislikes) {
        Set<Like> likes = new HashSet<>();
        for (int i = 0; i < totalNumberOfLikes; i++) {
            if (numberOfDislikes > 0) {
                likes.add(getLike(false));
                numberOfDislikes--;
            } else {
                likes.add(getLike());
            }
        }
        return likes;
    }

    public static Comment getComment() {
        Comment comment = new Comment();
        comment.setId(TEST_ID);
        comment.setRecipeComment(TEST_RECIPE_COMMENT);
        comment.setUser(getUser());
        return comment;
    }

    public static MockMultipartFile getMockMultipartFile (String content) {
        return new MockMultipartFile("fileName", TEST_FILENAME, null, content.getBytes());
    }
}
