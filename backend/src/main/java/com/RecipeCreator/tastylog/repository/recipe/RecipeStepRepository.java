package com.RecipeCreator.tastylog.repository.recipe;

import com.RecipeCreator.tastylog.entity.RecipeStep;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeStepRepository extends JpaRepository<RecipeStep,Long> {
}
