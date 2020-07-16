package com.bootcamp.nomnom.service;

import com.bootcamp.nomnom.entity.Comment;
import com.bootcamp.nomnom.entity.Like;
import com.bootcamp.nomnom.entity.Recipe;
import com.bootcamp.nomnom.entity.User;
import com.bootcamp.nomnom.repository.CommentRepository;
import com.bootcamp.nomnom.repository.LikeRepository;
import com.bootcamp.nomnom.repository.RecipeRepository;
import com.bootcamp.nomnom.repository.UserRepository;
import com.bootcamp.nomnom.util.StringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private LikeRepository likeRepository;

    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id.toString()));
    }

    public Set<Recipe> getAllRecipeByUser(Long id) {
        return recipeRepository.findByUser_Id(id);
    }

    //TODO: need to make so that if no file is uploaded for recipe then we use some sort of default image
    //TODO: proper returns and error handling
    public Recipe saveRecipe(Recipe recipe, User user, MultipartFile file) throws IOException {
        String dir = RecipeService.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "../resources";
        if (ImageIO.read(file.getInputStream()) != null) {
            String fileName = StringGenerator.getRandomFilename(file);
            Path filePath = Paths.get(dir + "/recipe-photos/" + fileName);
            try {
                Files.write(filePath, file.getBytes());
                recipe.setFileName(fileName);
                recipe.setUser(user);
                recipeRepository.save(recipe);
                return recipe;
            } catch (Exception e) {
                // need proper handling...
                e.printStackTrace();
            }
            // need to redirect to recipe page...

        } else {
            return null;
        }
        return recipe;
    }

    public Recipe updateRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
        return recipe;
    }

    public void updateRecipePicture() {
        //TODO write so it works with picture updating
        //returns recipe, changed it to void for the time being
        //so it doesn't throw errors
    }

    public void deleteRecipe(Recipe recipe) {
        Set<Like> recipeLikes = likeRepository.findByRecipe_Id(recipe.getId());
        Set<Comment> recipeComments = commentRepository.findByRecipe_Id(recipe.getId());
        for (Like like : recipeLikes) {
            likeRepository.delete(like);
        }
        for (Comment comment : recipeComments) {
            commentRepository.delete(comment);
        }
        recipeRepository.delete(recipe);
    }

    public Set<Comment> getAllComments(Long id) {
        return commentRepository.findByRecipe_Id(id);
    }

    public Set<Like> getAllLikes(Long id) {
        return likeRepository.findByRecipe_Id(id);
    }

}
