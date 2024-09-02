package com.RecipeCreator.tastylog.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Data
@Getter
@Setter
public class RecipeIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ingredientId;

    private String ingredientName;
    private String ingredientQuantity;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name ="recipe_id")
    private Recipe recipe;
}
