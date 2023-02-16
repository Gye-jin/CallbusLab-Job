package com.spring.zaritalk.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Zaritalk API TEST with Swagger").description("설명 부분").version("1.0").build();

	}

	@Bean
	public Docket api() {
		ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder.name("Authorization") //헤더 이름
        .description("JWT") //설명
        .modelRef(new ModelRef("string"))
        .parameterType("header") 
        .required(false)
        .build();

        List<Parameter> aParameters = new ArrayList<>();
        aParameters.add(aParameterBuilder.build());
        
	    return new Docket(DocumentationType.SWAGGER_2)
	    		.globalOperationParameters(aParameters)
                .forCodeGeneration(true)
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.newArrayList(apiKey()))
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.spring.zaritalk"))
                .build()
                .apiInfo(apiInfo())
                .enable(true);
	}

	
    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }
    
    // 추가
    private List<SecurityReference> defaultAuth() {
    	 AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
         AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
         authorizationScopes[0] = authorizationScope;
         return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }
 
    // 추가
    private ApiKey apiKey() {
    	return new ApiKey("JWT", "Authorization", "header");
    }
}