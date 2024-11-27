package com.RecipeCreator.tastylog.dto.response.recipe.manual;

import com.RecipeCreator.tastylog.entity.RecipeSpice;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RecipeSpiceDTO {

    @JsonProperty("spiceName")
    private final String spiceName;

    @JsonProperty("spiceQuantity")
    private final String spiceQuantity;

    @Builder
    public RecipeSpiceDTO(String spiceName, String spiceQuantity) {
        this.spiceName = spiceName;
        this.spiceQuantity = spiceQuantity;
    }
    public static RecipeSpiceDTO from(RecipeSpice spice){
        return RecipeSpiceDTO.builder()
                .spiceName(spice.getSpiceName())
                .spiceQuantity(spice.getSpiceQuantity())
                .build();
    }
}
