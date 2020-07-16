package com.bootcamp.nomnom.service;

import com.bootcamp.nomnom.entity.Like;
import com.bootcamp.nomnom.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RecipeLikeService {

    @Autowired
    private LikeRepository likeRepository;

    public Like saveLikeRegister(Like like) {
        likeRepository.save(like);
        return like;
    }


    public boolean updateLikeRegister(Long id, boolean like) {
        Like likeToUpdate = likeRepository.getOne(id);
        if (likeToUpdate.getRecipeLike()) {
            likeToUpdate.setRecipeLike(false);
        } else {
            likeToUpdate.setRecipeLike(true);
        }
        return likeToUpdate.getRecipeLike();
    }

}
