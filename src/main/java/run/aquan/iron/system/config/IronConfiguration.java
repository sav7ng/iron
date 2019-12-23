package run.aquan.iron.system.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import run.aquan.iron.security.filter.AdminAuthenticationFilter;

/**
 * @Class IronConfiguration
 * @Description TODO IRON项目配置文件
 * @Author Aquan
 * @Date 2019/12/23 15:35
 * @Version 1.0
 **/
@Slf4j
@Configuration
public class IronConfiguration {

    @Bean
    public FilterRegistrationBean<AdminAuthenticationFilter> adminAuthenticationFilter() {

        AdminAuthenticationFilter adminAuthenticationFilter = new AdminAuthenticationFilter();
        FilterRegistrationBean<AdminAuthenticationFilter> authenticationFilter = new FilterRegistrationBean<>();
        authenticationFilter.setFilter(adminAuthenticationFilter);
        authenticationFilter.addUrlPatterns("/api/admin/*");
        authenticationFilter.setOrder(0);

        return authenticationFilter;
    }

}
