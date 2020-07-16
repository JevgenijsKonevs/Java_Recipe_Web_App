package com.bootcamp.nomnom;

import com.bootcamp.nomnom.controller.RecipeController;
import com.bootcamp.nomnom.entity.Comment;
import com.bootcamp.nomnom.entity.Like;
import com.bootcamp.nomnom.entity.Recipe;
import com.bootcamp.nomnom.entity.User;
import com.bootcamp.nomnom.repository.CommentRepository;
import com.bootcamp.nomnom.repository.LikeRepository;
import com.bootcamp.nomnom.repository.RecipeRepository;
import com.bootcamp.nomnom.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.Set;

@SpringBootApplication
public class NomnomApplication implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(NomnomApplication.class);

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RecipeRepository recipeRepository;

	public static void main(String[] args) {
	    /*
		new File(RecipeController.dir).mkdir();
		new File(RecipeController.dir + "/recipe-photos").mkdir();
		new File(RecipeController.dir + "/user-photos").mkdir();
	     */
        SpringApplication.run(NomnomApplication.class, args);
    }
    @Override
    public void run(String... args) {


	        User user = userRepository.findByUsername("potato");
            user.setPassword("kekw");
            Set<Recipe> recipes = recipeRepository.findByUser_Id(user.getId());
            for(Recipe recipe : recipes){
                logger.warn(recipe.getTitle());
                logger.warn(recipe.getRecipeBody());
            }
            userRepository.save(user);
        user = userRepository.findByUsername("potato");
        logger.warn(user.getPassword());
        /*
         recipes = recipeRepository.findByUser_Id(user.getId());
        for(Recipe recipe : recipes){
            logger.warn(recipe.getTitle());
            logger.warn(recipe.getRecipeBody());
        }

         */
    }
}
