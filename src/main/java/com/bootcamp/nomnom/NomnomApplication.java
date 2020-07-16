package com.bootcamp.nomnom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NomnomApplication {

    public static void main(String[] args) {
	    /*
		new File(RecipeController.dir).mkdir();
		new File(RecipeController.dir + "/recipe-photos").mkdir();
		new File(RecipeController.dir + "/user-photos").mkdir();
	     */
        SpringApplication.run(NomnomApplication.class, args);
    }
}
