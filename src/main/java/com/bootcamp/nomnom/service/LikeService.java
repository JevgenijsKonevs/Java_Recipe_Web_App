package com.bootcamp.nomnom.service;

import com.bootcamp.nomnom.entity.Like;
import com.bootcamp.nomnom.entity.Recipe;
import com.bootcamp.nomnom.entity.User;
import com.bootcamp.nomnom.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    public Like saveLike(Like like) {
        likeRepository.save(like);
        return like;
    }

    public void deleteLikeById(Long id) {
        Like likeToUpdate = likeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id.toString()));
        likeRepository.delete(likeToUpdate);
    }

    public Like updateLike(Like like) {
        likeRepository.save(like);
        return like;
    }

    public long getRecipeLikes(Long recipeId) {
        long count = 0;
        Set<Like> likeSet = likeRepository.findByRecipe_Id(recipeId);
        for (Like like : likeSet) {
            if(like.getRecipeLike()) {
                count++;
            }
        }

        return count;
    }

    public long getRecipeDislikes(Long recipeId) {
        long count = 0;
        Set<Like> likeSet = likeRepository.findByRecipe_Id(recipeId);
        for (Like like : likeSet) {
            if(!(like.getRecipeLike())) {
                count++;
            }
        }

        return count;
    }

}
