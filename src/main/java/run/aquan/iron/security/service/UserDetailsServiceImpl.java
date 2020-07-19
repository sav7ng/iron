package run.aquan.iron.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import run.aquan.iron.security.entity.JwtUser;
import run.aquan.iron.system.enums.Datalevel;
import run.aquan.iron.system.exception.IronException;
import run.aquan.iron.system.mapper.UserMapper;
import run.aquan.iron.system.model.entity.User;

import javax.annotation.Resource;

/**
 * @Class UserDetailsServiceImpl
 * @Description TODO
 * @Author Aquan
 * @Date 2019/12/19 16:41
 * @Version 1.0
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByUsernameAndDatalevel(username, Datalevel.EFFECTIVE).orElseThrow(() -> new IronException("No user found"));
        return new JwtUser(user);
    }

}

