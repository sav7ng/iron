package run.aquan.iron.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import run.aquan.iron.security.entity.JwtUser;
import run.aquan.iron.system.model.entity.User;
import run.aquan.iron.system.service.UserService;

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
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByUserName(username);
        return new JwtUser(user);
    }

}

