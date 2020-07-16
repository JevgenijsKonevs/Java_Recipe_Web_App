package com.bootcamp.nomnom.repository;

import com.bootcamp.nomnom.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Set<Recipe> findByUser_Id(Long userId);

}
