package com.bootcamp.nomnom.controller;

import com.bootcamp.nomnom.entity.Comment;
import com.bootcamp.nomnom.entity.Like;
import com.bootcamp.nomnom.entity.Recipe;
import com.bootcamp.nomnom.entity.User;
import com.bootcamp.nomnom.service.CommentService;
import com.bootcamp.nomnom.service.LikeService;
import com.bootcamp.nomnom.service.RecipeService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RecipeControllerTest {

    private final String BASE_PATH = "/recipe";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeService recipeService;
    @MockBean
    private CommentService commentService;
    @MockBean
    private LikeService likeService;

    //Look at other Controller tests for a reference.
    //We are making very simple ones, only checking if right service methods are invoked and right template is returned.
    //More proper way would be to use MockMVC but we are low on time
    //Also Service tests are more important to have so focus on those! \o/
    @Test
    @DisplayName("GET /recipe")
    public void getAllRecipesTest() throws Exception {
        mockMvc.perform(get(BASE_PATH))
                .andExpect(redirectedUrl("/recipe/page/1"))
                .andExpect(status().isFound());
    }

    @Test
    public void getRecipesOnPageTest() throws Exception {
        List<Recipe> rl = Collections.singletonList(new Recipe());
        doReturn(new PageImpl<>(rl)).when(recipeService).listAll(5);

        mockMvc.perform(get(BASE_PATH + "/page/{pageNumber}", 5))
                .andExpect(status().isOk())
                .andExpect(model().attribute("totalPages", 1))
                .andExpect(model().attribute("currentPage", 5))
                .andExpect(model().attribute("recipes", rl));
    }

    @Test
    @Disabled
    public void recipePageTest() throws Exception {
        User u = new User();
        u.setId(1L);
        u.setUsername("User_1");

        List<Recipe> rl = Collections.singletonList(new Recipe());
        Recipe r = new Recipe();
        r.setUser(u);
        Set<Comment> c = new HashSet<>(Collections.singletonList(new Comment()));
        Set<Like> l = new HashSet<>(Collections.singletonList(new Like()));

        doReturn(r).when(recipeService).getRecipeById(1L);
        doReturn(c).when(recipeService).getAllComments(1L);
        doReturn(l).when(recipeService).getAllLikes(1L);

        mockMvc.perform(get(BASE_PATH + "/{recipeId}", 1L, u))
            .andExpect(status().isOk())
            .andExpect(model().attribute("recipe", r))
            .andExpect(model().attribute("recipeComments", c))
            .andExpect(model().attribute("recipeLikes", l))
            .andExpect(model().attribute("user", u));
    }

    @Test
    public void createRecipeTest() throws Exception {
        mockMvc.perform(get(BASE_PATH + "/new"))
                .andExpect(status().isOk());
    }

    @Test
    @Disabled
    public void postRecipeTest() throws Exception {
        doReturn(new Recipe()).when(recipeService).saveRecipe(any(Recipe.class), any(User.class), any(MultipartFile.class));
        mockMvc.perform(post(BASE_PATH))
                .andExpect(redirectedUrl(BASE_PATH))
                .andExpect(status().isOk());
    }
}
