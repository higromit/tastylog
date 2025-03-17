package com.RecipeCreator.tastylog.dto.response.recipe.manual;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Getter
public class GetRecipesResponse {

    @JsonProperty("recipes")
    private final List<RecipeDTO> recipes;

    @Builder
    public GetRecipesResponse(List<RecipeDTO> recipes){
        this.recipes=recipes;
    }
}
