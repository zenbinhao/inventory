package com.inventory.nike.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Profile({"dev"})
@EnableSwagger2
@Configuration
public class SwaggerConfiguration {
//    @Bean
//    public Docket ProductApi(){
//        return new Docket(DocumentationType.SWAGGER_2)
//                .genericModelSubstitutes(DeferredResult.class)
//                .useDefaultResponseMessages(false)
//                .forCodeGeneration(false)
//                .pathMapping("/")
//                .select()
//                .build()
//                .apiInfo(productApiInfo());
//    }
/**
 * @Author zengbh
 * @Description //TODO 初始化api文档
 * @Date 15:10 
 * @Param []
 * @return springfox.documentation.spring.web.plugins.Docket
 **/
    @Bean
    public Docket createRestApi() {
        // 定义全局header参数
//        List<Parameter> pars = new ArrayList<>();
//        ParameterBuilder authToken = new ParameterBuilder();
//        authToken.name("authToken").defaultValue("").description("身份认证id").modelRef(new ModelRef("string"))
//                .parameterType("header").required(false).build();
//        pars.add(authToken.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                //如果不想将所有的接口都通过swagger管理的话，可以将RequestHandlerSelectors.any()修改为RequestHandlerSelectors.basePackage()
                .apis(RequestHandlerSelectors.any())
//                .apis(RequestHandlerSelectors.basePackage("com.fish.server.manager.controller"))
                .paths(PathSelectors.any())
                .build()
//                .globalOperationParameters(pars)
                .apiInfo(productApiInfo()).securitySchemes(securitySchemes()).securityContexts(securityContexts());
    }

/**
 * @Author zengbh
 * @Description //TODO 页面上全局参数配置
 * @Date 15:09
 * @Param []
 * @return java.util.List<springfox.documentation.service.ApiKey>
 **/

    private List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeyList = new ArrayList<>();
        apiKeyList.add(new ApiKey("authToken", "authToken", "header"));
        return apiKeyList;
    }

/**
 * @Author zengbh
 * @Description //TODO 全局认证参数内容
 * @Date 15:10 
 * @Param []
 * @return java.util.List<springfox.documentation.spi.service.contexts.SecurityContext>
 **/
    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!login).*$"))
                        .build());
        return securityContexts;
    }


/**
 * @Author zengbh
 * @Description //TODO 全局参数认证的默认配置
 * @Date 15:11 
 * @Param []
 * @return java.util.List<springfox.documentation.service.SecurityReference>
 **/
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("authToken", authorizationScopes));
        return securityReferences;
    }


/**
 * @Author zengbh
 * @Description //TODO 文档信息描述  与  开启构建
 * @Date 15:11 
 * @Param []
 * @return springfox.documentation.service.ApiInfo
 **/
    private ApiInfo productApiInfo() {
        return new ApiInfoBuilder()
                .title("nike之家接口文档")
                .description("API 接口")
                .version("1.0")
                .build();
    }

}
