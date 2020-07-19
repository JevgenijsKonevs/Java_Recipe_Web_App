package com.bootcamp.nomnom.controller;

import com.bootcamp.nomnom.entity.Comment;
import com.bootcamp.nomnom.entity.Like;
import com.bootcamp.nomnom.entity.Recipe;
import com.bootcamp.nomnom.entity.User;
import com.bootcamp.nomnom.service.CommentService;
import com.bootcamp.nomnom.service.LikeService;
import com.bootcamp.nomnom.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Set;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeService likeService;

    @GetMapping()
    public String getAllRecipes(Model model) {
        return "recipes";
    }

    @GetMapping("/{recipeId}")
    public String recipePage(@PathVariable("recipeId") Long recipeId, Model model, @AuthenticationPrincipal User user) {

        Recipe recipe = recipeService.getRecipeById(recipeId);
        Set<Comment> recipeComments = recipeService.getAllComments(recipeId);
        Set<Like> recipeLikes = recipeService.getAllLikes(recipeId);

        model.addAttribute("recipe", recipe);
        model.addAttribute("recipeComments", recipeComments);
        model.addAttribute("recipeLikes", recipeLikes);
        model.addAttribute("user", user);
        return "recipe-page";
    }

    @GetMapping("/new")
    public String createRecipe() {
        return "new-recipe";
    }

    @PostMapping()
    public String postRecipe(@ModelAttribute Recipe recipe, @AuthenticationPrincipal User user, @RequestParam("file") MultipartFile file) throws IOException {
        recipeService.saveRecipe(recipe, user, file);
        return "redirect:/";
    }

    @GetMapping("/update/{recipeId}")
    public String editRecipe(Model model, @PathVariable("recipeId") Long recipeId, @AuthenticationPrincipal User user) {
        Recipe recipe = recipeService.getRecipeById(recipeId);
        model.addAttribute("recipe", recipe);
        model.addAttribute(user);
        return "edit-recipe";
    }

    @PostMapping("/update/{recipeId}")
    public String updateRecipe(@PathVariable("recipeId") Long recipeId, @ModelAttribute Recipe recipe, @AuthenticationPrincipal User user) {
        recipe.setId(recipeId);
        recipeValidation(recipeId, user.getId());
        recipeService.updateRecipe(recipe);
        return "redirect:/recipe/" + recipeId;
    }

    @GetMapping("/delete/{recipeId}")
    public String deleteRecipe(@PathVariable("recipeId") Long recipeId, @AuthenticationPrincipal User user) {
        recipeValidation(recipeId, user.getId());
        recipeService.deleteRecipeById(recipeId);
        return "redirect:/";
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
