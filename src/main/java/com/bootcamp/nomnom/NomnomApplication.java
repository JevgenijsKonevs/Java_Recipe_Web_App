package com.bootcamp.nomnom;

import com.bootcamp.nomnom.controller.RecipeController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class NomnomApplication {

	public static void main(String[] args) {
		new File(RecipeController.dir).mkdir();
		new File(RecipeController.dir + "/recipe-photos").mkdir();
		new File(RecipeController.dir + "/user-photos").mkdir();
		SpringApplication.run(NomnomApplication.class, args);
	}

}
