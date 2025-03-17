package com.RecipeCreator.tastylog.repository.recipe;

import com.RecipeCreator.tastylog.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface RecipeRepository extends JpaRepository<Recipe,Long> {
    List<Recipe> findByCategoryCategoryId(Long categoryId);

}
