package com.RecipeCreator.tastylog.dto.response.recipe.manual;

import com.RecipeCreator.tastylog.entity.Recipe;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class RecipeDTO {

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

    @NotNull
    @JsonProperty("categoryId")
    private final Long categoryId;

    @NotBlank
    @JsonProperty("recipeCreated")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}(:\\d{2})?$", message = "Invalid date format. Expected format: yyyy-MM-ddTHH:mm:ss")
    private String recipeCreated;

    @NotNull
    @JsonProperty("memberId")
    private final Long memberId;

    @JsonProperty("steps")
    private final List<RecipeStepDTO> steps;

    @JsonProperty("ingredients")
    private final List<RecipeIngredientDTO> ingredients;

    @JsonProperty("spices")
    private final List<RecipeSpiceDTO> spices;


    @Builder
    public RecipeDTO(String recipeTitle, String url, String recipeContent, String recipeAmount, String recipeTime, String recipeDifficulty, String recipeMainImage, String recipeTip, String recipeImage, Long categoryId, Long memberId, List<RecipeStepDTO> steps, List<RecipeIngredientDTO> ingredients, List<RecipeSpiceDTO> spices, LocalDateTime recipeCreated) {
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
        this.recipeCreated= String.valueOf(recipeCreated);
    }

    /**
     * from()메서드: Recipe 엔티티를 받아서 RecipeDTO로 변환
     * @param recipe
     * @return
     */
    public static RecipeDTO from(Recipe recipe){
        return RecipeDTO.builder()
                .recipeTitle(recipe.getRecipeTitle())
                .url(recipe.getUrl())
                .recipeContent(recipe.getRecipeContent())
                .recipeCreated(recipe.getRecipeCreated())
                .recipeAmount(recipe.getRecipeAmount())
                .recipeTime(recipe.getRecipeTime())
                .recipeDifficulty(recipe.getRecipeDifficulty())
                .recipeMainImage(recipe.getRecipeMainImage())
                .recipeTip(recipe.getRecipeTip())
                .recipeImage(recipe.getRecipeImage())
                .categoryId(recipe.getCategory().getCategoryId())
                .memberId(recipe.getMember().getMemberId())
                .steps(recipe.getSteps().stream()
                        .map(RecipeStepDTO::from).collect(Collectors.toList()))
                .ingredients(recipe.getIngredients().stream()
                        .map(RecipeIngredientDTO::from)
                        .collect(Collectors.toList()))
                .spices(recipe.getSpices().stream()
                        .map(RecipeSpiceDTO::from)
                        .collect(Collectors.toList()))

                .build();


    }
}
