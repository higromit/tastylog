package com.RecipeCreator.tastylog.service.recipe.youtube;

import com.RecipeCreator.tastylog.entity.Recipe;


public interface YoutubeRecipeService  {

    Recipe extractYoutubeRecipe(Long memberId, String url);

}
