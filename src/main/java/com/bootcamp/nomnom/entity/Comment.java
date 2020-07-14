package com.bootcamp.nomnom.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "recipe_comment")
public class Comment implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn
    private Recipe recipe;

    @Id
    @ManyToOne
    @JoinColumn
    private User user;

    private String recipeComment;

    public Comment(User user, String recipeComment) {
        this.user = user;
        this.recipeComment = recipeComment;
    }

    public Comment() {
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Comment)) return false;
        Comment that = (Comment) o;
        return Objects.equals(recipe.getId(), that.recipe.getId()) &&
                Objects.equals(user.getId(), that.user.getId()) &&
                Objects.equals(recipeComment, that.recipeComment);
    }

    @Override
    public int hashCode() {
        return(Objects.hash(recipe.getId(), user.getId(), recipeComment));
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

    public String getRecipeComment() {
        return recipeComment;
    }

    public void setRecipeComment(boolean recipeLike) {
        this.recipeComment = recipeComment;
    }

}
