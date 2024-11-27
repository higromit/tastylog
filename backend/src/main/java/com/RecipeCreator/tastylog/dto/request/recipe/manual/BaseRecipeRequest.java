package com.RecipeCreator.tastylog.dto.request.recipe.manual;

import com.RecipeCreator.tastylog.dto.response.recipe.manual.RecipeIngredientDTO;
import com.RecipeCreator.tastylog.dto.response.recipe.manual.RecipeSpiceDTO;
import com.RecipeCreator.tastylog.dto.response.recipe.manual.RecipeStepDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class BaseRecipeRequest {
    @NotBlank(message = "레시피명을 입력해주세요.")
    @JsonProperty("recipeTitle")
    private final String recipeTitle;


    @JsonProperty("url")
    private final String url;

    @JsonProperty("recipeContent")
    private final String recipeContent;


    @JsonProperty("recipeAmount")
    private final String recipeAmount;

    @JsonProperty("recipeTime")
    private final String recipeTime;

    @JsonProperty("recipeDifficulty")
    private final String recipeDifficulty;

    @JsonProperty("recipeMainImage")
    private final String recipeMainImage;

    @JsonProperty("recipeTip")
    private final String recipeTip;

    @JsonProperty("recipeImage")
    private final String recipeImage;

    @NotNull(message = "카테고리 ID를 입력해주세요.")
    @JsonProperty("categoryId")
    private final Long categoryId;

    @NotNull(message = "회원 ID를 입력해주세요.")
    @JsonProperty("memberId")
    private final Long memberId;

    @JsonProperty("steps")
    private final List<RecipeStepDTO> steps;

    @JsonProperty("ingredients")
    private final List<RecipeIngredientDTO> ingredients;

    @JsonProperty("spices")
    private final List<RecipeSpiceDTO> spices;


    @Builder
    public BaseRecipeRequest(String recipeTitle, String url, String recipeContent, String recipeAmount, String recipeTime, String recipeDifficulty, String recipeMainImage, String recipeTip, String recipeImage, Long categoryId, Long memberId, List<RecipeStepDTO> steps, List<RecipeIngredientDTO> ingredients, List<RecipeSpiceDTO> spices) {
        this.recipeTitle = recipeTitle;
        this.url = url;
        this.recipeContent = recipeContent;
        this.recipeAmount = recipeAmount;
        this.recipeTime = recipeTime;
        this.recipeDifficulty = recipeDifficulty;
        this.recipeMainImage = recipeMainImage;
        this.recipeTip = recipeTip;
        this.recipeImage = recipeImage;
        this.categoryId = categoryId;
        this.memberId = memberId;
        this.steps = steps;
        this.ingredients = ingredients;
        this.spices = spices;
    }
}
