package run.aquan.iron.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import run.aquan.iron.security.constants.SecurityConstant;
import run.aquan.iron.security.exception.JWTAccessDeniedHandler;
import run.aquan.iron.security.exception.JWTAuthenticationEntryPoint;
import run.aquan.iron.security.filter.JWTAuthorizationFilter;
import run.aquan.iron.security.service.UserDetailsServiceImpl;

import javax.annotation.Resource;
import java.util.Arrays;

import static java.util.Collections.singletonList;

/**
 * @Class SecurityConfig
 * @Description
 * @Author Saving
 * @Date 2019/12/19 17:07
 * @Version 1.0
 **/
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // TODO: 2020.11.28 @Saving SecurityConfig代码带重构优化 CORS

    @Resource
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Bean
    public UserDetailsService createUserDetailsService() {
        return userDetailsServiceImpl;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        // 设置自定义的userDetailsService以及密码编码器
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                // 禁用 CSRF
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, SecurityConstant.CONTENT_USER_LOGIN_URL).permitAll()
                .antMatchers(HttpMethod.POST, SecurityConstant.CONTENT_USER_REGISTER_URL).permitAll()
                .antMatchers(HttpMethod.POST, SecurityConstant.CONTENT_USER_REFRESH_TOKEN_URL).permitAll()
                .antMatchers(HttpMethod.POST, SecurityConstant.ADMIN_USER_LOGIN_URL).permitAll()
                .antMatchers(SecurityConstant.SWAGGER_URL.get(0)).permitAll()
                .antMatchers(SecurityConstant.SWAGGER_URL.get(1)).permitAll()
                .antMatchers(SecurityConstant.SWAGGER_URL.get(2)).permitAll()
                // 指定路径下的资源需要验证了的用户才能访问
                .antMatchers(SecurityConstant.ALL_URL).authenticated()
                .antMatchers(HttpMethod.DELETE, SecurityConstant.ALL_URL).hasRole("ADMIN")
                // 其他都放行了
                .anyRequest().permitAll()
                .and()
                //添加自定义Filter
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                // 不需要session（不创建会话）
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 授权异常处理
                .exceptionHandling().authenticationEntryPoint(new JWTAuthenticationEntryPoint())
                .accessDeniedHandler(new JWTAccessDeniedHandler());
        // 防止H2 web 页面的Frame 被拦截
        http.headers().frameOptions().disable();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(singletonList("*"));
        configuration.setAllowedHeaders(singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "OPTIONS"));
        configuration.setExposedHeaders(Arrays.asList(SecurityConstant.CONTEN_TOKEN_HEADER, SecurityConstant.ADMIN_TOKEN_HEADER));
        configuration.setAllowCredentials(false);
        configuration.setMaxAge(3600l);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
