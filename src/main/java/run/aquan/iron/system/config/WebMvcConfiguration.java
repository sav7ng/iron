package run.aquan.iron.system.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jackson.JsonComponentModule;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Class WebMvcConfigurer
 * @Description Spring MVC 配置
 * @Author Saving
 * @Date 2019/8/16 14:53
 * @Version 1.0
 **/
@Slf4j
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    private final PageableHandlerMethodArgumentResolver pageableResolver;

    private final SortHandlerMethodArgumentResolver sortResolver;

    public WebMvcConfiguration(PageableHandlerMethodArgumentResolver pageableResolver, SortHandlerMethodArgumentResolver sortResolver) {
        this.pageableResolver = pageableResolver;
        this.sortResolver = sortResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        // 注册Spring data jpa pageable的参数分解器
        resolvers.add(pageableResolver);
        resolvers.add(sortResolver);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.stream()
                .filter(c -> c instanceof StringHttpMessageConverter)
                .findFirst()
                .ifPresent(converter -> {
                    StringHttpMessageConverter stringHttpMessageConverter = (StringHttpMessageConverter) converter;
                    stringHttpMessageConverter.setDefaultCharset(StandardCharsets.UTF_8);
                });
        converters.stream()
                .filter(c -> c instanceof MappingJackson2HttpMessageConverter)
                .findFirst()
                .ifPresent(converter -> {
                    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = (MappingJackson2HttpMessageConverter) converter;
                    Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder.json();
                    JsonComponentModule module = new JsonComponentModule();
                    ObjectMapper objectMapper = builder.modules(module).build();
                    mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
                });
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(false);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // registry.addViewController("/swagger-ui")
        //         .setViewName("forward:/swagger-ui/index.html");
        // TODO: 2020.11.27 addViewController实现不了
        registry.addRedirectViewController("/swagger-ui", "/swagger-ui/index.html");
    }


}
