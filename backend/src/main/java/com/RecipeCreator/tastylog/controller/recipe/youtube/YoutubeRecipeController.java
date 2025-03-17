package com.RecipeCreator.tastylog.controller.recipe.youtube;


import com.RecipeCreator.tastylog.entity.Recipe;
import com.RecipeCreator.tastylog.service.recipe.youtube.YoutubeRecipeService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @Tag(name = "유튜브 크롤링 API", description = "유튜브 스크립트를 크롤링할 수 있는 API입니다")
    @PostMapping("/crawlYoutube")
    public Recipe crawlAndSaveRecipe(@RequestParam Long memberId, @RequestParam String url){
        return  youtubeRecipeService.crawlAndSaveRecipe(memberId,url);
    }
}
