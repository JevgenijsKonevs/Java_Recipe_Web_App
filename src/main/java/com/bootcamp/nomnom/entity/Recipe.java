package com.bootcamp.nomnom.entity;

import javax.persistence.*;

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String Title;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Lob
    private String recipeBody;


    //TODO A way to store an image. Simple way?: auto generated file name stored as string
    // and images itself are stored in the resources. Then you find them by the name.


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRecipeBody() {
        return recipeBody;
    }

    public void setRecipeBody(String recipeBody) {
        this.recipeBody = recipeBody;
    }
}