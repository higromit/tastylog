package com.RecipeCreator.tastylog.service.recipe.website;

import com.RecipeCreator.tastylog.entity.Recipe;

public interface WebsiteRecipeService {

    Recipe crawlWebsiteRecipe(Long memberId,String url);
    Recipe crawlAndSaveRecipe(Long memberId, String url);

}
