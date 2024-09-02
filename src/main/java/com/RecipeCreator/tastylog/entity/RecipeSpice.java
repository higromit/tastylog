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
public class RecipeSpice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeSpiceId;

    private String spiceName;
    private String spiceQuantity;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;


}
