package com.RecipeCreator.tastylog.service.recipe.website.impl;

import com.RecipeCreator.tastylog.entity.*;
import com.RecipeCreator.tastylog.repository.recipe.CategoryRepository;
import com.RecipeCreator.tastylog.repository.recipe.MemberRepository;
import com.RecipeCreator.tastylog.repository.recipe.RecipeRepository;
import com.RecipeCreator.tastylog.service.recipe.website.WebsiteRecipeService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WebsiteRecipeServiceImpl implements WebsiteRecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MemberRepository memberRepository;

    public Recipe crawlWebsiteRecipe(Long memberId, String url) {



        Recipe recipe = new Recipe();
        try {
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(()-> new RuntimeException("Member not found"));
            recipe.setMember(member);

            Document doc = Jsoup.connect(url).get();

            // 레시피 제목
            Element titleElement = doc.selectFirst(".view2_summary h3");
            String recipeTitle = titleElement != null ? titleElement.text() : "";
            recipe.setRecipeTitle(recipeTitle);
            recipe.setUrl(url);

            //레시피 설명
            Element contnetElement = doc.selectFirst(".view2_summary_in");
            String recipeContent = contnetElement != null ? contnetElement.text() : "";
            recipe.setRecipeContent(recipeContent);

//            view2_summary_info1
            // 인원
            Element amountElement = doc.selectFirst(".view2_summary_info1");
            String recipeAmount = amountElement != null ? amountElement.text() : "";
            recipe.setRecipeAmount(recipeAmount);

            // 시간
            Element timeElement = doc.selectFirst(".view2_summary_info2");
            String recipeTime = timeElement != null ? timeElement.text() : "";
            recipe.setRecipeTime(recipeTime);

            // 난이도
            Element difficultyElement = doc.selectFirst(".view2_summary_info3");
            String recipeDifficulty = difficultyElement != null ? difficultyElement.text() : "";
            recipe.setRecipeDifficulty(recipeDifficulty);

            // 메인 이미지
            Element mainImageElement = doc.selectFirst(".centeredcrop img");
            String mainImage = mainImageElement != null ? mainImageElement.attr("src") : "";
            recipe.setRecipeMainImage(mainImage);

            // 조리 양
//            Element timeInfoElement = doc.selectFirst(".view2_summary_info1");
//            String recipeAmount = timeInfoElement != null ? timeInfoElement.text() : "";
//            recipe.setRecipeAmount(recipeAmount);

            // 재료 및 양념 구분 크롤링
            Elements ingredientSections = doc.select(".ready_ingre3 b");
            Elements ingredientLists = doc.select(".ready_ingre3 ul");

            List<RecipeIngredient> ingredients = new ArrayList<>();
            List<RecipeSpice> spices = new ArrayList<>();

            for (int i = 0; i < ingredientSections.size(); i++) {
                String sectionName = ingredientSections.get(i).text();
                Elements items = ingredientLists.get(i).select("li");

                for (Element item : items) {
                    String quantity = item.selectFirst("span").text();
                    String name = item.selectFirst(".ingre_list_name").text();

                    if (sectionName.contains("양념")) {
                        RecipeSpice spice = new RecipeSpice();
                        spice.setSpiceName(name);
                        spice.setSpiceQuantity(quantity);
                        spices.add(spice);
                    } else {
                        RecipeIngredient ingredient = new RecipeIngredient();
                        ingredient.setIngredientName(name);
                        ingredient.setIngredientQuantity(quantity);
                        ingredients.add(ingredient);
                    }
                }
            }

            recipe.setIngredients(ingredients);
            recipe.setSpices(spices);

            // 조리 순서 및 이미지 크롤링
            Elements stepsElements = doc.select(".view_step .view_step_cont");
            Elements stepImages = doc.select(".view_step .view_step_cont img");
            List<RecipeStep> steps = new ArrayList<>();
            for (int i = 0; i < stepsElements.size(); i++) {
                RecipeStep step = new RecipeStep();
                step.setStepOrder(i + 1);
                step.setStepContent(stepsElements.get(i).text());

                // 단계 이미지 추가
                String stepImage = i < stepImages.size() ? stepImages.get(i).attr("src") : null;
                step.setStepImage(stepImage);

                steps.add(step);
            }

            recipe.setSteps(steps);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return recipe;
    }
    public Recipe crawlAndSaveRecipe(Long memberId, String url) {


        Recipe recipe = new Recipe();
        try {

            Member member = memberRepository.findById(memberId)
                    .orElseThrow(()-> new RuntimeException("Member not found"));
            recipe.setMember(member);


            Category defaultCategory = categoryRepository.findByCategoryName("디폴트")
                    .orElseGet(() -> {
                        Category newCategory = new Category();
                        newCategory.setCategoryName("디폴트");

                        return categoryRepository.save(newCategory);
                    });

            recipe.setCategory(defaultCategory);


            Document doc = Jsoup.connect(url).get();

            // 레시피 제목
            Element titleElement = doc.selectFirst(".view2_summary h3");
            String recipeTitle = titleElement != null ? titleElement.text() : "";
            recipe.setRecipeTitle(recipeTitle);
            recipe.setUrl(url);

            // 메인 이미지
            Element mainImageElement = doc.selectFirst(".centeredcrop img");
            String mainImage = mainImageElement != null ? mainImageElement.attr("src") : "";
            recipe.setRecipeMainImage(mainImage);

            // 조리 양
            Element timeInfoElement = doc.selectFirst(".view2_summary_info1");
            String recipeAmount = timeInfoElement != null ? timeInfoElement.text() : "";
            recipe.setRecipeAmount(recipeAmount);

            // 재료 및 양념 구분 크롤링
            Elements ingredientSections = doc.select(".ready_ingre3 b");
            Elements ingredientLists = doc.select(".ready_ingre3 ul");

            List<RecipeIngredient> ingredients = new ArrayList<>();
            List<RecipeSpice> spices = new ArrayList<>();

            for (int i = 0; i < ingredientSections.size(); i++) {
                String sectionName = ingredientSections.get(i).text();
                Elements items = ingredientLists.get(i).select("li");

                for (Element item : items) {

                    String quantity = item.selectFirst("span").text().trim();
                    String name = item.text().replace(quantity, "").trim();


                    if (sectionName.contains("양념")) {
                        RecipeSpice spice = new RecipeSpice();
                        spice.setSpiceName(name);
                        spice.setSpiceQuantity(quantity);
                        spice.setRecipe(recipe);  // 연관 관계 설정
                        spices.add(spice);
                    } else {
                        RecipeIngredient ingredient = new RecipeIngredient();
                        ingredient.setIngredientName(name);
                        ingredient.setIngredientQuantity(quantity);
                        ingredient.setRecipe(recipe);  // 연관 관계 설정
                        ingredients.add(ingredient);
                    }
                }
            }

            recipe.setIngredients(ingredients);
            recipe.setSpices(spices);

            // 조리 순서 및 이미지 크롤링
            Elements stepsElements = doc.select(".view_step .view_step_cont");
            Elements stepImages = doc.select(".view_step .view_step_cont img");
            List<RecipeStep> steps = new ArrayList<>();
            for (int i = 0; i < stepsElements.size(); i++) {
                RecipeStep step = new RecipeStep();
                step.setStepOrder(i + 1);
                step.setStepContent(stepsElements.get(i).text());

                // 단계 이미지 추가
                String stepImage = i < stepImages.size() ? stepImages.get(i).attr("src") : null;
                step.setStepImage(stepImage);
                step.setRecipe(recipe);  // 연관 관계 설정

                steps.add(step);
            }

            recipe.setSteps(steps);

            // 크롤링한 데이터를 DB에 저장
            Recipe savedRecipe = recipeRepository.save(recipe);  // DB에 저장하고 저장된 객체를 반환
            return savedRecipe;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return recipe;
    }
}
