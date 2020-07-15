package com.bootcamp.nomnom.entity;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String fileName;

    @Lob
    private String recipeBody;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private Set<Like> likes;

    public Recipe(String title, Like... likes) {
        this.title = title;
        for(Like like : likes) like.setRecipe(this);
        this.likes = Stream.of(likes).collect(Collectors.toSet());
    }

    public Recipe() {
    }

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private Set<Comment> comments;

    public Recipe(String title, Comment... comments) {
        this.title = title;
        for(Comment comment : comments) comment.setRecipe(this);
        this.comments = Stream.of(comments).collect(Collectors.toSet());
    }


    //TODO A way to store an image. Simple way?: auto generated file name stored as string
    // and images itself are stored in the resources. Then you find them by the name.


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRecipeBody() {
        return recipeBody;
    }

    public void setRecipeBody(String recipeBody) {
        this.recipeBody = recipeBody;
    }

    public Set<Like> getLikes() {
        return likes;
    }

    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}