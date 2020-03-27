package run.aquan.iron.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import run.aquan.iron.security.entity.JwtUser;
import run.aquan.iron.system.model.entity.SysUser;
import run.aquan.iron.system.service.SysUserService;

import javax.annotation.Resource;

/**
 * @Class SysUserDetailsServiceImpl
 * @Description TODO
 * @Author Aquan
 * @Date 2019/12/24 10:43
 * @Version 1.0
 **/
@Service
public class SysUserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.findUserByUserName(username);
        return new JwtUser(sysUser);
    }

}