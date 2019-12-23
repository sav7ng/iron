package run.aquan.iron.security.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Class AdminAuthenticationFilter
 * @Description TODO Admin后台管理用户认证过滤器
 * @Author Aquan
 * @Date 2019/12/23 15:28
 * @Version 1.0
 **/
@Slf4j
public class AdminAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //对请求进行预处理
        log.info("过滤器开始对请求进行预处理");
        HttpServletRequest request = httpServletRequest;
        String requestUri = request.getRequestURI();
        System.out.println("请求的接口为：" + requestUri);
        long startTime = System.currentTimeMillis();
        //通过 doFilter 方法实现过滤功能
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        // 上面的 doFilter 方法执行结束后用户的请求已经返回
        long endTime = System.currentTimeMillis();
        log.info("该用户的请求已经处理完毕，请求花费的时间为：" + (endTime - startTime));

    }

}
