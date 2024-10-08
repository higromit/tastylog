package com.RecipeCreator.tastylog.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;




@Configuration
@OpenAPIDefinition(
        info = @Info(title = "tastylog 어플리케이션을 위한 API",
                                description = "요리 메모장 tastylog API문서",
                                version = "v1"))
public class SwaggerConfig {


}
