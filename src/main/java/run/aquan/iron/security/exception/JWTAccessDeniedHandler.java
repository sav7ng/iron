package run.aquan.iron.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import run.aquan.iron.system.model.support.BaseResponse;
import run.aquan.iron.system.utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Class JWTAccessDeniedHandler
 * @Description AccessDeineHandler 用来解决认证过的用户访问无权限资源时的异常
 * @Author Saving
 * @Date 2019/12/19 17:03
 * @Version 1.0
 **/
public class JWTAccessDeniedHandler implements AccessDeniedHandler {

    private ObjectMapper objectMapper = JsonUtils.DEFAULT_JSON_MAPPER;

    /**
     * 当用户尝试访问需要权限才能的REST资源而权限不足的时候，
     * 将调用此方法发送401响应以及错误信息
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        BaseResponse<Object> errorDetail = new BaseResponse<>();
        errorDetail.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        errorDetail.setMessage(accessDeniedException.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(objectMapper.writeValueAsString(errorDetail));
    }
}
