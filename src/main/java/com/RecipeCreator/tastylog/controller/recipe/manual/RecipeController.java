package com.RecipeCreator.tastylog.controller.recipe.manual;

import com.RecipeCreator.tastylog.dto.request.recipe.manual.BaseRecipeRequest;
import com.RecipeCreator.tastylog.dto.response.recipe.manual.GetCategoryRecipesResponse;
import com.RecipeCreator.tastylog.dto.response.recipe.manual.GetRecipeResponse;
import com.RecipeCreator.tastylog.dto.response.recipe.manual.GetRecipesResponse;
import com.RecipeCreator.tastylog.entity.Recipe;
import com.RecipeCreator.tastylog.service.recipe.manual.RecipeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/recipe/manual")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @Tag(name = "레시피 리스트 조회 API", description = "저장된 레시피들을 조회할 수 있는 API입니다")
    @GetMapping
    public ResponseEntity<GetRecipesResponse> getRecipes(){
        GetRecipesResponse response = recipeService.getRecipes();
        return ResponseEntity.ok(response);

    }
    @Tag(name = "레시피 조회 API", description = "하나의 레시피를 조회할 수 있는 API입니다")
    @GetMapping("/{id}")
    public ResponseEntity<GetRecipeResponse> getRecipe(@PathVariable Long id){
        GetRecipeResponse response = recipeService.getRecipe(id);
        return ResponseEntity.ok(response);
    }
    @Tag(name = "레시피 생성 API", description = "하나의 레세피를 생성할 수 있는 API입니다")
    @PostMapping
    public ResponseEntity<Recipe> submitRecipe(@Valid @RequestBody BaseRecipeRequest request){
        Recipe recipe = recipeService.submitRecipe(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(recipe);
    }
    @Tag(name = "레시피 수정 API", description = "하나의 레세피를 수정할 수 있는 API입니다")
    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody BaseRecipeRequest request){
        Recipe updatedRecipe = recipeService.updateRecipe(id, request);
        return ResponseEntity.ok().build();
    }

    @Tag(name = "레시피 삭제 API", description = "하나의 레세피를 삭제할 수 있는 API입니다")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id){
        recipeService.deleteRecipe(id);
        return ResponseEntity.ok().build();
    }
    @Tag(name = "카테고리별 레시피 조회 API", description = "선택한 카테고리의 레시피들을 조회할 수 있는 API입니다")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<GetCategoryRecipesResponse> getCategoryRecipes(@PathVariable Long categoryId){
        GetCategoryRecipesResponse response =recipeService.getCategoryRecipes(categoryId);
        return ResponseEntity.ok(response);
    }



}
