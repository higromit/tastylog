package com.RecipeCreator.tastylog.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Getter
@Setter
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId;

    private String recipeTitle;

    private String url;

    @Lob
    private String recipeContent;

    @Column(updatable = false)
    private LocalDateTime recipeCreated;

    private String recipeAmount;

    private String recipeTime;

    private String recipeDifficulty;

    private String recipeMainImage;

    @Lob
    private String recipeTip;

    private String recipeImage;

    @ManyToOne
    @JoinColumn(name ="member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "recipe",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<RecipeStep> steps;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<RecipeIngredient> ingredients;

    @OneToMany(mappedBy = "recipe",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<RecipeSpice> spices;

    @PrePersist
    protected void onCreate() {
        this.recipeCreated = LocalDateTime.now();
    }
}
