package run.aquan.iron.security.entity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import run.aquan.iron.security.service.SysUserDetailsServiceImpl;
import run.aquan.iron.security.service.UserDetailsServiceImpl;

import javax.annotation.Resource;

/**
 * @Class CurrentUser
 * @Description 获取当前请求的用户
 * @Author Saving
 * @Date 2019/12/19 16:39
 * @Version 1.0
 **/
@Component
public class CurrentUser {

    @Resource
    private UserDetailsServiceImpl userDetailsService;

    @Resource
    private SysUserDetailsServiceImpl sysUserDetailsService;

    public JwtUser getCurrentUser() {
        return (JwtUser) userDetailsService.loadUserByUsername(getCurrentUserName());
    }

    public JwtUser getCurrentSysUser() {
        return (JwtUser) sysUserDetailsService.loadUserByUsername(getCurrentUserName());
    }

    /**
     * TODO: 由于在JWTAuthorizationFilter这个类注入UserDetailsServiceImpl一致失败，
     *  导致无法正确查找到用户，所以存入Authentication的Principal为从 token 中取出的当前用户的姓名
     */
    private static String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }

}
