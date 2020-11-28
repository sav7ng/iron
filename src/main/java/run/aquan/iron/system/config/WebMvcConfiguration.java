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


    //使用阿里 FastJson 作为JSON MessageConverter
    // @Override
    // public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    //     FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
    //     FastJsonConfig config = new FastJsonConfig();
    //     config.setSerializerFeatures(SerializerFeature.WriteMapNullValue);//保留空的字段
    //     //SerializerFeature.WriteNullStringAsEmpty,//String null -> ""
    //     //SerializerFeature.WriteNullNumberAsZero//Number null -> 0
    //     // 按需配置，更多参考FastJson文档哈
    //
    //     converter.setFastJsonConfig(config);
    //     // converter.setDefaultCharset(Charset.forName("UTF-8"));
    //     // converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
    //     converter.setDefaultCharset(StandardCharsets.UTF_8);
    //     converter.setSupportedMediaTypes(Collections.singletonList(new MediaType("application", "json", StandardCharsets.UTF_8)));
    //     converters.add(converter);
    // }

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

    //解决跨域问题
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                //暴露header中的其他属性给客户端应用程序
                //如果不设置这个属性前端无法通过response header获取到Authorization也就是token
                .exposedHeaders("Authorization")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600);
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

}
