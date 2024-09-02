package com.RecipeCreator.tastylog.controller.recipe.youtube;


import com.RecipeCreator.tastylog.entity.Recipe;
import com.RecipeCreator.tastylog.service.recipe.youtube.YoutubeRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recipe/youtube")
public class YoutubeRecipeController {

    @Autowired
    private YoutubeRecipeService youtubeRecipeService;

    @PostMapping
    public Recipe crawlAndSaveRecipe(@RequestParam Long memberId, @RequestParam String url){
        return  youtubeRecipeService.crawlAndSaveRecipe(memberId,url);
    }
}
