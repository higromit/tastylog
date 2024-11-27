package com.RecipeCreator.tastylog.dto.response.recipe.manual;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetRecipeResponse {
    @JsonProperty("recipe")
    private final RecipeDTO recipe;

    @Builder
    public GetRecipeResponse(RecipeDTO recipe){
        this.recipe = recipe;
    }
}
