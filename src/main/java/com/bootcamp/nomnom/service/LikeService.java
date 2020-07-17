package com.bootcamp.nomnom.service;

import com.bootcamp.nomnom.entity.Like;
import com.bootcamp.nomnom.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

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

}
