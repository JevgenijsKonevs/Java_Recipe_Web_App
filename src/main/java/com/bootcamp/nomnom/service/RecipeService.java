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
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    private Path absolutePath = Paths.get("./src/main/resources/static/images/recipe");

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

    public Page<Recipe> listAll(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 3);
        return recipeRepository.findAll(pageable);
    }

    //TODO: proper returns and error handling
    public Recipe saveRecipe(Recipe recipe, User user, MultipartFile file) throws IOException {
        String sanitizedRecipeHTML = Jsoup.clean(recipe.getRecipeBody(), Whitelist.simpleText().addTags("h2", "h3", "h4", "li", "ul", "ol"));
        recipe.setRecipeBody(sanitizedRecipeHTML);
        if (file.isEmpty()) {
            recipe.setFileName("default.png");
            recipe.setUser(user);
            recipeRepository.save(recipe);
            return recipe;
        } else {
            if (ImageIO.read(file.getInputStream()) != null) {
                String fileName = StringGenerator.getRandomFilename(file);
                Path absolutePath = Paths.get(".");
                Path filePath = Paths.get(absolutePath + "/src/main/resources/static/images/recipe/" + fileName);
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

            } else {
                return null;
            }
        }

        return recipe;
    }

    public Recipe updateRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
        return recipe;
    }

    public Recipe updateRecipePicture(Recipe recipe, MultipartFile file) {
        if (file.isEmpty()) {
            return recipe;
        } else {
            String toDelete = recipe.getFileName();
            String fileName = StringGenerator.getRandomFilename(file);
            Path filePath = Paths.get(absolutePath + "/" + fileName);
            try {
                Files.write(filePath, file.getBytes());
                recipe.setFileName(fileName);
                if (!("default.png".equals(toDelete))) {
                    Path pathToDelete = Paths.get(absolutePath + "/" + toDelete);
                    Files.delete(pathToDelete);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return recipe;
        }
    }

    public Recipe deleteRecipePicture(Recipe recipe) throws IOException {
        if (!("default.png").equals(recipe.getFileName())) {
            Path deletePath = Paths.get(absolutePath + "/" + recipe.getFileName());
            Files.delete(deletePath);
            recipe.setFileName("default.png");
        }

        return recipe;
    }

    public void deleteRecipeById(Long id) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
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
