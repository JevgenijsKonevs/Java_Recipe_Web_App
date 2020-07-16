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

    //not figured out
    public String updateLikeRegister(Long id) {
        Like likeToUpdate = likeRepository.getOne(id);
        likeRepository.delete(likeToUpdate);
        return "Like updated";
    }

}
