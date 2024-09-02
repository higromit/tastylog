package com.RecipeCreator.tastylog.dto.response.recipe.manual;

import com.RecipeCreator.tastylog.entity.RecipeIngredient;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RecipeIngredientDTO {

    @JsonProperty("ingredientName")
    private final String ingredientName;

    @JsonProperty("ingredientQuantity")
    private final String ingredientQuantity;

    @Builder
    public RecipeIngredientDTO(String ingredientName, String ingredientQuantity) {
        this.ingredientName=ingredientName;
        this.ingredientQuantity=ingredientQuantity;
    }

    public static RecipeIngredientDTO from(RecipeIngredient ingredient){
        return RecipeIngredientDTO.builder()
                .ingredientName(ingredient.getIngredientName())
                .ingredientQuantity(ingredient.getIngredientQuantity())
                .build();
    }
}
