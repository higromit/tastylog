package com.RecipeCreator.tastylog.controller.recipe.website;

import com.RecipeCreator.tastylog.entity.Recipe;
import com.RecipeCreator.tastylog.service.recipe.website.WebsiteRecipeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;


@RestController
@RequestMapping("/api/recipe/website")
public class WebsiteRecipeController {

    @Autowired
    private WebsiteRecipeService recipeService;


    @Tag(name = "웹사이트 레시피 가져오기 API", description = "크롤링을 이용해 만개의 레시피 사이트에서 레시피 정를 가져오는 API입니다.")
    @GetMapping("/crawl")
    public Recipe getRecipe(@RequestParam Long memberId, @RequestParam String url) {
        return recipeService.crawlWebsiteRecipe(memberId,url);
    }

    @Tag(name = "웹사이트 레시피 가져오기 및 저장 API", description = "크롤링을 이용해 만개의 레시피 사이트에서 레시피 정보를 가져온 후 DB에 저장하는 API입니다.")
    @GetMapping("/crawlSave")
    public Recipe crawlAndSaveRecipe(@RequestParam Long memberId, @RequestParam String url) {
        return recipeService.crawlAndSaveRecipe(memberId, url);
    }

}

