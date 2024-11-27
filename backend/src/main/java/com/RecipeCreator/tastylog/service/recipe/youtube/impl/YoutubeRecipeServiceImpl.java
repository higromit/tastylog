package com.RecipeCreator.tastylog.service.recipe.youtube.impl;

import com.RecipeCreator.tastylog.entity.Category;
import com.RecipeCreator.tastylog.entity.Member;
import com.RecipeCreator.tastylog.entity.Recipe;
import com.RecipeCreator.tastylog.entity.RecipeStep;
import com.RecipeCreator.tastylog.exception.RecipeErrorCode;
import com.RecipeCreator.tastylog.exception.RecipeException;
import com.RecipeCreator.tastylog.repository.recipe.CategoryRepository;
import com.RecipeCreator.tastylog.repository.recipe.MemberRepository;
import com.RecipeCreator.tastylog.repository.recipe.RecipeRepository;
import com.RecipeCreator.tastylog.service.recipe.youtube.YoutubeRecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

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
    public Recipe crawlAndSaveRecipe(Long memberId, String url) {

        Recipe recipe = new Recipe();

        // YouTube URL에서 제목, 설명 등을 가져오기 위한 크롤링 로직
        try {
            if (!isValidUrl(url)) {
                throw new RecipeException(RecipeErrorCode.INVALID_URL.getCode(),
                        RecipeErrorCode.INVALID_URL.getMessage());
            }

            Member member = memberRepository.findById(memberId)
                    .orElseThrow(()-> new RecipeException(RecipeErrorCode.MEMBER_NOT_FOUND.getCode(), "멤버 정보가 없습니다: "+ memberId));
            recipe.setMember(member);

            Category defaultCategory = categoryRepository.findByCategoryName("디폴트")
                    .orElseGet(() -> {
                        Category newCategory = new Category();
                        newCategory.setCategoryName("디폴트");

                        return categoryRepository.save(newCategory);
                    });

            recipe.setCategory(defaultCategory);



            // 유튜브 메타데이터 추출 (제목, 설명 등)
            Document doc = Jsoup.connect(url).get();
            String title = doc.title();
            String content = doc.select("meta[name=description]").attr("content");

            recipe.setRecipeTitle(title);
            recipe.setUrl(url);
            recipe.setRecipeContent(content);

            // 여기서 PyTube나 다른 라이브러리를 사용해 자막을 추출하거나, YouTube Transcript API를 호출
            String transcriptJson = getTranscriptFromYoutube(url);

            // JSON 데이터를 Recipe에 맞게 파싱하여 자막 및 요리 단계를 추가
            List<RecipeStep> steps = parseTranscriptToRecipeSteps(transcriptJson);
            recipe.setSteps(steps);

        } catch (MalformedURLException e) {
            throw new RecipeException(RecipeErrorCode.INVALID_URL.getCode(),
                    "URL 형식이 잘못되었습니다: " + url);
        } catch (IOException e) {
            throw new RecipeException(RecipeErrorCode.API_RESPONSE_ERROR.getCode(),
                    RecipeErrorCode.API_RESPONSE_ERROR.getMessage());
        }

        return recipeRepository.save(recipe);
    }


    private String getTranscriptFromYoutube(String youtubeUrl) {
        // YouTubeTranscript API 또는 yt-dlp 같은 도구로 자막을 가져오는 로직 구현
        // 필요 시, Python 코드를 Java에서 실행하여 결과를 가져오는 방법도 고려 가능
        return ""; // 추출된 자막의 JSON 데이터 반환
    }

    private List<RecipeStep> parseTranscriptToRecipeSteps(String transcriptJson) {
        // JSON 데이터를 파싱하여 RecipeStep 객체로 변환하는 로직 구현
        ObjectMapper mapper = new ObjectMapper();
        // mapper를 사용하여 JSON 데이터를 RecipeStep 리스트로 변환
        return List.of(); // 파싱된 RecipeStep 리스트 반환
    }

    private boolean isValidUrl(String url){
        try {
            new URL(url);
            return true;
        }catch (MalformedURLException e){
            return  false;
        }
    }


}

