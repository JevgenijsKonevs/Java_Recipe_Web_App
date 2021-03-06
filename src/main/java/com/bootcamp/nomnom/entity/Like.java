package com.bootcamp.nomnom.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "recipe_like")
public class Like implements Serializable {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean recipeLike;

    public Like() {
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean getRecipeLike() {
        return recipeLike;
    }

    public void setRecipeLike(boolean recipeLike) {
        this.recipeLike = recipeLike;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
