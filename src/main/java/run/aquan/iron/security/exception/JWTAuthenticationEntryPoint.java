package run.aquan.iron.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import run.aquan.iron.system.model.support.BaseResponse;
import run.aquan.iron.system.utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Class JWTAuthenticationEntryPoint
 * @Description AuthenticationEntryPoint 用来解决匿名用户访问需要权限才能访问的资源时的异常
 * @Author Saving
 * @Date 2019/12/19 17:04
 * @Version 1.0
 **/
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private ObjectMapper objectMapper = JsonUtils.DEFAULT_JSON_MAPPER;

    /**
     * 当用户尝试访问需要权限才能的REST资源而不提供Token或者Token错误或者过期时，
     * 将调用此方法发送401响应以及错误信息
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        BaseResponse<Object> errorDetail = new BaseResponse<>();
        errorDetail.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        errorDetail.setMessage(authException.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(objectMapper.writeValueAsString(errorDetail));
    }
}
