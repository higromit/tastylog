package com.RecipeCreator.tastylog.service.recipe.youtube.impl;

import com.RecipeCreator.tastylog.entity.Category;
import com.RecipeCreator.tastylog.entity.Member;
import com.RecipeCreator.tastylog.entity.Recipe;
import com.RecipeCreator.tastylog.entity.RecipeStep;
import com.RecipeCreator.tastylog.repository.recipe.CategoryRepository;
import com.RecipeCreator.tastylog.repository.recipe.MemberRepository;
import com.RecipeCreator.tastylog.repository.recipe.RecipeRepository;
import com.RecipeCreator.tastylog.service.recipe.youtube.YoutubeRecipeService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
public class YoutubeRecipeServiceImpl implements YoutubeRecipeService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private CategoryRepository categoryRepository;



    @Override
    public Recipe extractYoutubeRecipe(Long memberId, String url) {
        Recipe recipe = new Recipe();
        try {
            // 멤버 조회
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new RuntimeException("Member not found"));
            recipe.setMember(member);

            // 기본 카테고리 조회 또는 생성
            Category defaultCategory = categoryRepository.findByCategoryName("디폴트")
                    .orElseGet(() -> {
                        Category newCategory = new Category();
                        newCategory.setCategoryName("디폴트");
                        return categoryRepository.save(newCategory);
                    });
            recipe.setCategory(defaultCategory);

            // 외부 파이썬 파일 실행
            String[] command = new String[]{"python3", "youtubeRecipeCrawling.py", url};
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.directory(new File("crawling/"));
            Process process = pb.start();

            // 표준 출력 스트림 읽기
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            // 에러 스트림 별도 스레드에서 읽기
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            new Thread(() -> {
                String errorLine;
                try {
                    while ((errorLine = errorReader.readLine()) != null) {
                        System.err.println(errorLine);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // 프로세스 타임아웃 설정
            if (!process.waitFor(30, TimeUnit.SECONDS)) {
                process.destroy();
                throw new RuntimeException("Python script execution timed out");
            }

            // recipe_summary.txt 파일을 읽어 레시피 정보 추출
            File textFile = new File("crawling/recipe_summary.txt");
            if (!textFile.exists()) {
                throw new FileNotFoundException("Text file not found: " + textFile.getAbsolutePath());
            }

            // 텍스트 파일 파싱
            try (BufferedReader fileReader = new BufferedReader(new FileReader(textFile))) {
                String line;
                String recipeName = null;
                String recipeUrl = null;
                String recipeContent = null;
                List<RecipeStep> recipeSteps = new ArrayList<>();
                String currentStepContent = null;
                int currentStepOrder = 1;

                while ((line = fileReader.readLine()) != null) {
                    line = line.trim();
                    if (line.startsWith("레시피명 :")) {
                        recipeName = line.substring("레시피명 :".length()).trim();
                    } else if (line.startsWith("URL :")) {
                        recipeUrl = line.substring("URL :".length()).trim();
                    } else if (line.startsWith("요리 소개 :")) {
                        recipeContent = line.substring("요리 소개 :".length()).trim();
                    } else if (line.startsWith("재료 :")) {
                        // 재료는 필요에 따라 추가 처리
                    } else if (line.startsWith("단계")) {
                        currentStepContent = line;
                    } else if (line.matches("\\d+단계\\(.*\\) : .*")) {
                        String stepLine = line.substring(line.indexOf(":") + 1).trim();
                        stepLine = stepLine.replaceAll("^\\d+\\)\\s*|\\(.*\\)\\s*|:\\s*", "").trim();
                        RecipeStep step = new RecipeStep();
                        step.setStepContent(stepLine);
                        step.setStepOrder(currentStepOrder++);
                        step.setRecipe(recipe);
                        recipeSteps.add(step);
                    }
                }

                // Recipe 객체에 정보 설정
                recipe.setRecipeTitle(recipeName);
                recipe.setUrl(recipeUrl);
                recipe.setRecipeContent(recipeContent);
                recipe.setSteps(recipeSteps);
            }


        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to execute Python script or read JSON file", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Python script execution was interrupted", e);
        }

        return recipeRepository.save(recipe);
    }

    private String getTranscriptFromYoutube(String youtubeUrl) {
        return "";
    }

    private List<RecipeStep> parseTranscriptToRecipeSteps(String transcriptJson) {
        return List.of();
    }


}