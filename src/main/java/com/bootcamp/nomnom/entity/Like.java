package com.bootcamp.nomnom.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "recipe_like")
public class Like implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn
    private Recipe recipe;

    @Id
    @ManyToOne
    @JoinColumn
    private User user;

    private boolean recipeLike;

    public Like(User user, boolean recipeLike) {
        this.user = user;
        this.recipeLike = recipeLike;
    }

    public Like() {
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Like)) return false;
        Like that = (Like) o;
        return Objects.equals(recipe.getId(), that.recipe.getId()) &&
                Objects.equals(user.getId(), that.user.getId()) &&
                Objects.equals(recipeLike, that.recipeLike);
    }

    @Override
    public int hashCode() {
        return(Objects.hash(recipe.getId(), user.getId(), recipeLike));
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

}
