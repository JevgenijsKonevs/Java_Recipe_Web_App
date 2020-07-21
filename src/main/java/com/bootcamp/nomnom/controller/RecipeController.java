package com.bootcamp.nomnom.controller;

import com.bootcamp.nomnom.entity.Comment;
import com.bootcamp.nomnom.entity.Like;
import com.bootcamp.nomnom.entity.Recipe;
import com.bootcamp.nomnom.entity.User;
import com.bootcamp.nomnom.service.CommentService;
import com.bootcamp.nomnom.service.LikeService;
import com.bootcamp.nomnom.service.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

    Logger logger = LoggerFactory.getLogger(RecipeController.class);

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeService likeService;

    @GetMapping("")
    public String getAllRecipes(Model model) {
        return "redirect:/recipe/page/1";
    }

    @GetMapping("/page/{pageNumber}")
    public String getRecipesOnPage(Model model, @PathVariable("pageNumber") int pageNumber) {
        Page<Recipe> page = recipeService.listAll(pageNumber);
        List<Recipe> recipeList = page.getContent();
        int totalPages = page.getTotalPages();
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("recipes", recipeList);
        return "recipes";
    }

    @GetMapping("/{recipeId}")
    public String recipePage(@PathVariable("recipeId") Long recipeId, Model model, @AuthenticationPrincipal User user) {

        Recipe recipe = recipeService.getRecipeById(recipeId);
        Set<Comment> recipeComments = recipeService.getAllComments(recipeId);
        Set<Like> recipeLikes = recipeService.getAllLikes(recipeId);

        model.addAttribute("recipe", recipe);
        logger.warn("is user null?  " + (recipe.getUser() == null));
        model.addAttribute("recipeComments", recipeComments);
        model.addAttribute("recipeLikes", recipeLikes);
        model.addAttribute("user", user);
        return "recipe";
    }

    @GetMapping("/new")
    public String createRecipe() {
        return "new-recipe";
    }

    @PostMapping()
    public String postRecipe(@ModelAttribute Recipe recipe, @AuthenticationPrincipal User user, @RequestParam("file") MultipartFile file) throws IOException {
        recipeService.saveRecipe(recipe, user, file);
        return "redirect:/recipe/";
    }

    @GetMapping("/update/{recipeId}")
    public String editRecipe(Model model, @PathVariable("recipeId") Long recipeId, @AuthenticationPrincipal User user) {
        Recipe recipe = recipeService.getRecipeById(recipeId);
        model.addAttribute("recipe", recipe);
        model.addAttribute("user", user);
        return "edit-recipe";
    }

    @PostMapping("/update/{recipeId}")
    public String updateRecipe(@PathVariable("recipeId") Long recipeId, @ModelAttribute Recipe recipe, @AuthenticationPrincipal User user, @RequestParam("file") MultipartFile file) throws IOException {
        recipe.setId(recipeId);
        recipeValidation(recipeId, user.getId());
        if(!file.isEmpty()) {
            recipeService.saveRecipe(recipe,user,file);
        } else {
            recipeService.updateRecipeWithoutImages(recipe, user);
        }

        return "redirect:/recipe/" + recipeId;
    }

    @GetMapping("/delete/{recipeId}")
    public String deleteRecipe(@PathVariable("recipeId") Long recipeId, @AuthenticationPrincipal User user) {
        recipeValidation(recipeId, user.getId());
        recipeService.deleteRecipeById(recipeId);
        return "redirect:/recipe/";
    }

    @PostMapping("/{recipeId}/comment")
    public String addComment(@PathVariable("recipeId") Long recipeId, @ModelAttribute Comment comment, @AuthenticationPrincipal User user) {
        comment.setRecipe(recipeService.getRecipeById(recipeId));
        comment.setUser(user);
        commentService.saveComment(comment);
        return "redirect:/recipe/" + recipeId;
    }

    @GetMapping("/{recipeId}/comment/delete/{commentId}")
    public String deleteComment(@PathVariable("recipeId") Long recipeId, @PathVariable("commentId") Long commentId, @AuthenticationPrincipal User user) {
        Comment commentValidation = commentService.getCommentById(commentId);
        if (!(user.getId()).equals(commentValidation.getUser().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        commentService.deleteComment(commentId);
        return "redirect:/recipe/" + recipeId;
    }

    @PostMapping("/{recipeId}/like/{likeValue}")
    public String submitLike(@PathVariable("recipeId") Long recipeId, @PathVariable("likeValue") boolean recipeLike, @AuthenticationPrincipal User user) {
        Recipe recipe = recipeService.getRecipeById(recipeId);
        Like like = new Like();
        like.setRecipe(recipe);
        like.setUser(user);
        likeService.saveLike(like);
        return "redirect:/recipe/" + recipeId;
    }

    private void recipeValidation(Long recipeId, Long userId) {
        Recipe recipeTest = recipeService.getRecipeById(recipeId);
        if (!recipeTest.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
