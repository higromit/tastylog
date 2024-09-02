package com.RecipeCreator.tastylog.service.recipe.manual.Impl;

import com.RecipeCreator.tastylog.dto.response.recipe.manual.RecipeDTO;
import com.RecipeCreator.tastylog.dto.request.recipe.manual.BaseRecipeRequest;
import com.RecipeCreator.tastylog.dto.response.recipe.manual.GetCategoryRecipesResponse;
import com.RecipeCreator.tastylog.dto.response.recipe.manual.GetRecipeResponse;
import com.RecipeCreator.tastylog.dto.response.recipe.manual.GetRecipesResponse;
import com.RecipeCreator.tastylog.entity.*;
import com.RecipeCreator.tastylog.repository.recipe.CategoryRepository;
import com.RecipeCreator.tastylog.repository.recipe.MemberRepository;
import com.RecipeCreator.tastylog.repository.recipe.RecipeRepository;
import com.RecipeCreator.tastylog.service.recipe.manual.RecipeService;
import com.RecipeCreator.tastylog.util.RecipeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;

    public GetRecipesResponse getRecipes(){
        List<Recipe> recipes = recipeRepository.findAll();
        List<RecipeDTO> recipeDTOS = recipes.stream().map(RecipeDTO::from).collect(Collectors.toList());
        GetRecipesResponse response = new GetRecipesResponse(recipeDTOS);

        return response;
    }

    public GetRecipeResponse getRecipe(Long id){
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Recipe not found"));
        RecipeDTO recipeDTO = RecipeDTO.from(recipe);
       GetRecipeResponse response = new GetRecipeResponse(recipeDTO);
        return response;

    }
    //카테고리 별로 보여주기 위함
    public GetCategoryRecipesResponse getCategoryRecipes(Long categoryId){
        List<Recipe> recipes = recipeRepository.findByCategoryCategoryId(categoryId);
        List<RecipeDTO> recipeDTOS =recipes.stream().map(RecipeDTO::from).collect(Collectors.toList());
        GetCategoryRecipesResponse response = new GetCategoryRecipesResponse(recipeDTOS);
        return response;
    }

    public Recipe submitRecipe(BaseRecipeRequest request){
        Recipe recipe = new Recipe();
        RecipeMapper.mapSubmitReqeustToEntity(request, recipe,categoryRepository,memberRepository);
        return recipeRepository.save(recipe);
    }

    public Recipe updateRecipe(Long id, BaseRecipeRequest request){
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Recipe not found"));
        RecipeMapper.mapUpdateRequestToEntity(request,recipe,categoryRepository,memberRepository);
        return recipeRepository.save(recipe);
    }

    public void deleteRecipe(Long id){
        recipeRepository.deleteById(id);
    }






}
