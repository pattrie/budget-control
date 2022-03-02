package com.github.pattrie.budgetcontrol.configurations.springfox;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableWebMvc
@EnableSwagger2
@Configuration
public class SpringFoxConfig {

  @Value("${springfox.pathMapping:/}")
  private String springFoxPathMapping;

  @Bean
  public Docket docket() {
    return new Docket(DocumentationType.OAS_30)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.github.pattrie.budgetcontrol"))
        .paths(PathSelectors.any())
        .build()
        .pathMapping(springFoxPathMapping);
  }

}
