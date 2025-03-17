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
public class RecipeStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeStepId;

    @Lob
    private String StepContent;
    private String stepImage;
    private Integer stepOrder;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    @JsonBackReference
    private Recipe recipe;

}
