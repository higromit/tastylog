package com.RecipeCreator.tastylog.repository.recipe;

import com.RecipeCreator.tastylog.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
