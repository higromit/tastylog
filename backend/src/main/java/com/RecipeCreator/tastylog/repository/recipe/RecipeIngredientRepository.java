package com.RecipeCreator.tastylog.repository.recipe;

import com.RecipeCreator.tastylog.entity.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient,Long> {
}
