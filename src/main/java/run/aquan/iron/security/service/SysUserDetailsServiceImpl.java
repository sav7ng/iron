package run.aquan.iron.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import run.aquan.iron.security.entity.JwtUser;
import run.aquan.iron.system.enums.Datalevel;
import run.aquan.iron.system.exception.IronException;
import run.aquan.iron.system.mapper.SysUserMapper;
import run.aquan.iron.system.model.entity.SysUser;

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
    private SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserMapper.findByUsernameAndDatalevel(username, Datalevel.EFFECTIVE).orElseThrow(() -> new IronException("No user found"));
        return new JwtUser(sysUser);
    }

}