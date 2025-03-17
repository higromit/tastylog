package com.RecipeCreator.tastylog.repository.recipe;

import com.RecipeCreator.tastylog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByCategoryName(String name);
}
