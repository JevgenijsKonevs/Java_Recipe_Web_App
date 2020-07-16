package com.bootcamp.nomnom.repository;

import com.bootcamp.nomnom.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Set<Like> findByRecipe_Id(Long recipeId);

}
