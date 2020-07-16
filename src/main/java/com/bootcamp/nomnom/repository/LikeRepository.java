package com.bootcamp.nomnom.repository;

import java.util.Set;

import com.bootcamp.nomnom.entity.Like;
import com.bootcamp.nomnom.entity.Recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Set<Like> findByRecipe_Id(Long recipeId);

}
