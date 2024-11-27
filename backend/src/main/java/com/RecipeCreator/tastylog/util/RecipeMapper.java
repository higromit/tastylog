package com.RecipeCreator.tastylog.util;

import com.RecipeCreator.tastylog.dto.request.recipe.manual.BaseRecipeRequest;
import com.RecipeCreator.tastylog.entity.*;
import com.RecipeCreator.tastylog.repository.recipe.CategoryRepository;
import com.RecipeCreator.tastylog.repository.recipe.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;

public class RecipeMapper {
    public static void mapSubmitReqeustToEntity(BaseRecipeRequest request, Recipe recipe,
                                                CategoryRepository categoryRepository, MemberRepository memberRepository){
        mapCommonFields(request, recipe,categoryRepository,memberRepository);
        // SubmitArticleRequest에서만 처리할 추가 로직이 있다면 여기에 작성

    }

    public static void mapUpdateRequestToEntity(BaseRecipeRequest request, Recipe recipe, CategoryRepository categoryRepository, MemberRepository memberRepository){
        mapCommonFields(request,recipe,categoryRepository,memberRepository);
        // UpdateArticleRequest에서만 처리할 추가 로직이 있다면 여기에 작성


    }

    private static void mapCommonFields(BaseRecipeRequest request, Recipe recipe, CategoryRepository categoryRepository, MemberRepository memberRepository){
        recipe.setRecipeTitle(request.getRecipeTitle());
        recipe.setUrl(request.getUrl());
        recipe.setRecipeAmount(request.getRecipeAmount());
        recipe.setRecipeTime(request.getRecipeTime());
        recipe.setRecipeDifficulty(request.getRecipeDifficulty());
        recipe.setRecipeMainImage(request.getRecipeMainImage());
        recipe.setRecipeTip(request.getRecipeTip());
        recipe.setRecipeImage(request.getRecipeImage());

        Long categoryId = request.getCategoryId();
        if (categoryId == null) {
            throw new IllegalArgumentException("Category ID must not be null!");
        }


        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(()->new RuntimeException("Category not found"));
        recipe.setCategory(category);

        Long memberId = request.getMemberId();
        if (memberId == null) {
            throw new IllegalArgumentException("Member ID must not be null!");
        }

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(()-> new RuntimeException("member not found"));
        recipe.setMember(member);

        if(request.getSteps() !=null){
            List<RecipeStep> steps = request.getSteps().stream().map(recipeStepDTO ->
            {
                RecipeStep step = new RecipeStep();
                step.setStepContent(recipeStepDTO.getStepContent());
                step.setStepImage(recipeStepDTO.getStepImage());
                step.setStepOrder(recipeStepDTO.getStepOrder());
                step.setRecipe(recipe);
                return step;
            }).collect(Collectors.toList());
            recipe.setSteps(steps);
        }

        if (request.getIngredients() != null) {
            List<RecipeIngredient> ingredients = request.getIngredients().stream().map(ingredientDTO -> {
                RecipeIngredient ingredient = new RecipeIngredient();
                ingredient.setIngredientName(ingredientDTO.getIngredientName());
                ingredient.setIngredientQuantity(ingredientDTO.getIngredientQuantity());
                ingredient.setRecipe(recipe);
                return ingredient;
            }).collect(Collectors.toList());
            recipe.setIngredients(ingredients);
        }

        if (request.getSpices() != null) {
            List<RecipeSpice> spices = request.getSpices().stream().map(spiceDTO -> {
                RecipeSpice spice = new RecipeSpice();
                spice.setSpiceName(spiceDTO.getSpiceName());
                spice.setSpiceQuantity(spiceDTO.getSpiceQuantity());
                spice.setRecipe(recipe);
                return spice;
            }).collect(Collectors.toList());
            recipe.setSpices(spices);
        }

    }
}
