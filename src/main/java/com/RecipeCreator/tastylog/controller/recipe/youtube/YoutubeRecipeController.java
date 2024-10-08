package com.RecipeCreator.tastylog.controller.recipe.youtube;


import com.RecipeCreator.tastylog.entity.Recipe;
import com.RecipeCreator.tastylog.service.recipe.youtube.YoutubeRecipeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recipe/youtube")
public class YoutubeRecipeController {

    @Autowired
    private YoutubeRecipeService youtubeRecipeService;

    @Tag(name = "유튜브 레시피 가져오기 API",description = "유튜브의 레시피를 OPENAI를 통해 요약해서 가져오는 API입니다.")
    @GetMapping("/extractYoutube")
    public Recipe extractYoutubeRecipe(@RequestParam Long memberId, @RequestParam String url){
        return youtubeRecipeService.extractYoutubeRecipe(memberId, url);
    }
}
