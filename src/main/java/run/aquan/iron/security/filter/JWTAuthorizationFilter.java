package run.aquan.iron.security.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import run.aquan.iron.security.constants.SecurityConstant;
import run.aquan.iron.security.utils.JwtTokenUtil;
import run.aquan.iron.system.exception.TokenExpirationException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Class JWTAuthorizationFilter
 * @Description 过滤器处理所有HTTP请求，并检查是否存在带有正确令牌的Authorization标头。例如，如果令牌未过期或签名密钥正确。
 * @Author Saving
 * @Date 2019/12/19 16:54
 * @Version 1.0
 **/
@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String apiAuthorization = request.getHeader(SecurityConstant.TOKEN_HEADER);
        String adminAuthorization = request.getHeader(SecurityConstant.ADMIN_TOKEN_HEADER);
        // 如果请求头中没有token信息则直接放行了
        if ((apiAuthorization == null || !apiAuthorization.startsWith(SecurityConstant.TOKEN_PREFIX)) && (adminAuthorization == null || !adminAuthorization.startsWith(SecurityConstant.TOKEN_PREFIX))) {
            SecurityContextHolder.clearContext();
            chain.doFilter(request, response);
            return;
        }
        Boolean admin = StringUtils.isNotBlank(adminAuthorization);
        String authorization = admin ? adminAuthorization : apiAuthorization;
        UsernamePasswordAuthenticationToken authentication = getAuthentication(authorization, admin);
        // 如果请求头中有token，则进行解析，并且设置授权信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        super.doFilterInternal(request, response, chain);
    }

    /**
     * 获取用户认证信息 Authentication
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String authorization, Boolean admin) {
        String token = authorization.replace(SecurityConstant.TOKEN_PREFIX, "");
        try {
            Boolean check = JwtTokenUtil.checkToken(token, admin);
            if (check) {
                String username = JwtTokenUtil.getUsernameByToken(token);
                log.info("checking username:" + username);
                // 通过 token 获取用户具有的角色
                List<SimpleGrantedAuthority> userRolesByToken = JwtTokenUtil.getUserRolesByToken(token);
                if (StringUtils.isNotBlank(username)) {
                    return new UsernamePasswordAuthenticationToken(username, null, userRolesByToken);
                }
            } else {
                throw new TokenExpirationException();
            }
        } catch (SignatureException | ExpiredJwtException | MalformedJwtException | IllegalArgumentException | TokenExpirationException exception) {
            log.warn("Request to parse JWT with invalid signature . Detail : " + exception.getMessage());
        }
        return null;
    }

}
