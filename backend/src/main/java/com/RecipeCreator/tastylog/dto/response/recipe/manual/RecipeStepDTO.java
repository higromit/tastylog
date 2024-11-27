package com.RecipeCreator.tastylog.dto.response.recipe.manual;

import com.RecipeCreator.tastylog.entity.RecipeStep;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RecipeStepDTO {
    @JsonProperty("stepContent")
    private final String stepContent;

    @JsonProperty("stepImage")
    private final String stepImage;

    @JsonProperty("stepOrder")
    private final int stepOrder;

    @Builder
    public RecipeStepDTO(String stepContent, String stepImage, int stepOrder) {
        this.stepContent = stepContent;
        this.stepImage = stepImage;
        this.stepOrder = stepOrder;
    }

    public static RecipeStepDTO from(RecipeStep recipeStep){
        return RecipeStepDTO.builder()
                .stepContent(recipeStep.getStepContent())
                .stepImage(recipeStep.getStepImage())
                .stepOrder(recipeStep.getStepOrder())
                .build();
    }
}
