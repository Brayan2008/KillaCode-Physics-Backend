package app.killacode.back_app.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "KillaCode Physics API",
        version = "1.0",
        summary = "API REST para la aplicación KillaCode Physics",
        description = "Una API RESTful construida con Spring Boot que proporciona funcionalidades \n para la gestión de prácticas, teoría, ejercicios y puntuaciones en la aplicación KillaCode Physics. \nPermite a los usuarios acceder a recursos educativos o de aprendizaje relacionados con la física de manera eficiente y estructurada."
    )
)
public class OpenAPIConf {
    
}
