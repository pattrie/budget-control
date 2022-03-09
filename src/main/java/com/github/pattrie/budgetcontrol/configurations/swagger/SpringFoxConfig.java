package com.github.pattrie.budgetcontrol.configurations.swagger;

import com.github.pattrie.budgetcontrol.domains.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableWebMvc
@EnableSwagger2
@Configuration
public class SpringFoxConfig implements WebMvcConfigurer {

  @Value("${springfox.pathMapping:/}")
  private String springFoxPathMapping;

  @Bean
  public Docket docket() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(getApiInfo())
        .securityContexts(List.of(securityContext()))
        .securitySchemes(List.of(apiKey()))
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.github.pattrie.budgetcontrol"))
        .paths(PathSelectors.any())
        .build()
        .pathMapping(springFoxPathMapping)
        .ignoredParameterTypes(User.class);
  }

  private ApiInfo getApiInfo() {
    return new ApiInfoBuilder()
        .title("API Budget Control")
        .description("Budget Control Application")
        .version("1.0.0")
        .license("License of API")
        .contact(new Contact("Patrícia Morais", "https://github.com/pattrie", "patri.b.morais@gmail.com"))
        .build();
  }

  private ApiKey apiKey() {
    return new ApiKey("JWT", "Authorization", "header");
  }

  private SecurityContext securityContext() {
    return SecurityContext.builder().securityReferences(defaultAuth()).build();
  }

  private List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return List.of(new SecurityReference("JWT", authorizationScopes));
  }
}
