package run.aquan.iron.system.config;

import com.fasterxml.classmate.TypeResolver;
import io.swagger.models.auth.In;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import run.aquan.iron.security.constants.SecurityConstant;
import run.aquan.iron.system.constants.IronConstant;
import springfox.documentation.builders.*;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRuleConvention;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

/**
 * @Class SwaggerConfigurer
 * @Description TODO SwaggerAPI接口文档
 * @Author Aquan
 * @Date 2019/8/16 14:52
 * @Version 1.0
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket createContentApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(IronConstant.CONTENT_CONTROLLER_PACKAGE)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(IronConstant.CONTENT_CONTROLLER_PACKAGE))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(contentApiKeys())
                .securityContexts(contentSecurityContext());
    }

    @Bean
    public Docket createAdminApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(IronConstant.ADMIN_CONTROLLER_PACKAGE)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(IronConstant.ADMIN_CONTROLLER_PACKAGE))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(adminApiKeys())
                .securityContexts(adminSecurityContext());
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("IRON_API")
                .description("爱敲代码的猫")
                .termsOfServiceUrl("https://aquan.run")
                .contact(new Contact("Aquan","https://aquan.run","853029827@qq.com"))
                .version("0.1")
                .build();
    }

    private List<ApiKey> adminApiKeys() {
        return Arrays.asList(new ApiKey("Token from header", SecurityConstant.ADMIN_TOKEN_HEADER, In.HEADER.name()));
    }

    private List<SecurityContext> adminSecurityContext() {
        return Collections.singletonList(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("/api/admin/.*"))
                        .build()
        );
    }

    private List<ApiKey> contentApiKeys() {
        return Arrays.asList(new ApiKey("Access key from header", SecurityConstant.TOKEN_HEADER, In.HEADER.name()));
    }

    private List<SecurityContext> contentSecurityContext() {
        return Collections.singletonList(
                SecurityContext.builder()
                        .securityReferences(contentApiAuth())
                        .forPaths(PathSelectors.regex("/api/content/.*"))
                        .build()
        );
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope[] authorizationScopes = {new AuthorizationScope("Admin api", "Access admin api")};
        return Arrays.asList(new SecurityReference("Token from header", authorizationScopes));
    }

    private List<SecurityReference> contentApiAuth() {
        AuthorizationScope[] authorizationScopes = {new AuthorizationScope("content api", "Access content api")};
        return Arrays.asList(new SecurityReference("Access key from header", authorizationScopes));
    }

    @Bean
    public AlternateTypeRuleConvention customizeConvention(TypeResolver resolver) {
        return new AlternateTypeRuleConvention() {
            @Override
            public int getOrder() {
                return Ordered.LOWEST_PRECEDENCE;
            }

            @Override
            public List<AlternateTypeRule> rules() {
                return Arrays.asList(
                        newRule(resolver.resolve(Pageable.class), resolver.resolve(pageableMixin())),
                        newRule(resolver.resolve(Sort.class), resolver.resolve(sortMixin())));
            }
        };
    }

    private Type sortMixin() {
        return new AlternateTypeBuilder()
                .fullyQualifiedClassName(String.format("%s.generated.%s", Sort.class.getPackage().getName(), Sort.class.getSimpleName()))
                .withProperties(Collections.singletonList(property(String[].class, "sort")))
                .build();
    }

    private Type pageableMixin() {
        return new AlternateTypeBuilder()
                .fullyQualifiedClassName(String.format("%s.generated.%s", Pageable.class.getPackage().getName(), Pageable.class.getSimpleName()))
                .withProperties(Arrays.asList(property(Integer.class, "page"), property(Integer.class, "size"), property(String[].class, "sort")))
                .build();
    }

    private AlternateTypePropertyBuilder property(Class<?> type, String name) {
        return new AlternateTypePropertyBuilder()
                .withName(name)
                .withType(type)
                .withCanRead(true)
                .withCanWrite(true);
    }

}
