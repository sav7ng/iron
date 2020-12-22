package run.aquan.iron.system.config.swagger;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import run.aquan.iron.security.constants.SecurityConstant;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

import static java.util.Collections.singletonList;

/**
 * @author Saving
 * @version 1.1
 * @description SwaggerAPI接口文档
 * @date 2020.12.22 19:19
 **/
@EnableOpenApi
@Configuration
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerConfiguration {

    /**
     * 配置属性
     */
    private final SwaggerProperties properties;

    public SwaggerConfiguration(SwaggerProperties properties) {
        this.properties = properties;
    }

    private ApiKey apiKey(String header) {
        return new ApiKey(header, "Authorization", "header");
    }

    private SecurityContext securityContext(String header) {
        return SecurityContext.builder().securityReferences(defaultAuth(header)).build();
    }

    private List<SecurityReference> defaultAuth(String header) {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return singletonList(new SecurityReference(header, authorizationScopes));
    }

    /**
     * API信息
     */
    private ApiInfo apiInfo(SwaggerProperties.SwaggerEntity swaggerEntity) {
        return new ApiInfoBuilder()
                .title(swaggerEntity.getTitle())
                .description(swaggerEntity.getDescription())
                .version(swaggerEntity.getVersion())
                .contact(new Contact(swaggerEntity.getContactName(), swaggerEntity.getContactUrl(), swaggerEntity.getContactEmail()))
                .build();
    }

    /**
     * 前端API
     */
    @Bean
    public Docket frontApi() {
        return new Docket(DocumentationType.OAS_30)
                .enable(properties.getEnable())
                .groupName(properties.getFront().getGroupName())
                .apiInfo(apiInfo(properties.getFront()))
                .securityContexts(singletonList(securityContext(SecurityConstant.CONTEN_TOKEN_HEADER)))
                .securitySchemes(singletonList(apiKey(SecurityConstant.CONTEN_TOKEN_HEADER)))
                .select()
                .apis(RequestHandlerSelectors.basePackage(properties.getFront().getBasePackage()))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 后台API
     */
    @Bean
    public Docket backApi() {
        return new Docket(DocumentationType.OAS_30)
                .enable(properties.getEnable())
                .groupName(properties.getBack().getGroupName())
                .apiInfo(apiInfo(properties.getBack()))
                .securityContexts(singletonList(securityContext(SecurityConstant.ADMIN_TOKEN_HEADER)))
                .securitySchemes(singletonList(apiKey(SecurityConstant.ADMIN_TOKEN_HEADER)))
                .select()
                .apis(RequestHandlerSelectors.basePackage(properties.getBack().getBasePackage()))
                .paths(PathSelectors.any())
                .build();
    }

}
