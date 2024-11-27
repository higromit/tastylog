package com.RecipeCreator.tastylog.service.recipe.manual;

import com.RecipeCreator.tastylog.dto.request.recipe.manual.BaseRecipeRequest;
import com.RecipeCreator.tastylog.dto.response.recipe.manual.GetCategoryRecipesResponse;
import com.RecipeCreator.tastylog.dto.response.recipe.manual.GetRecipeResponse;
import com.RecipeCreator.tastylog.dto.response.recipe.manual.GetRecipesResponse;
import com.RecipeCreator.tastylog.entity.Recipe;

public interface RecipeService {

    GetRecipesResponse getRecipes();

    GetRecipeResponse getRecipe(Long id);

    GetCategoryRecipesResponse getCategoryRecipes(Long categoryId);

    Recipe submitRecipe(BaseRecipeRequest request);

    Recipe updateRecipe(Long id, BaseRecipeRequest request);

    void deleteRecipe(Long id);
}
