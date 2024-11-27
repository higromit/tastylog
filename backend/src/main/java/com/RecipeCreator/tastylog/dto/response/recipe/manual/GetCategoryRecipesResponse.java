package com.RecipeCreator.tastylog.dto.response.recipe.manual;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Getter
public class GetCategoryRecipesResponse {

    @JsonProperty("recipes")
    private List<RecipeDTO> recipes;

    @Builder
    public GetCategoryRecipesResponse(List<RecipeDTO> recipes){
        this.recipes=recipes;
    }
}
