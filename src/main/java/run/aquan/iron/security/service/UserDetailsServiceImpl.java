package run.aquan.iron.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import run.aquan.iron.security.entity.JwtUser;
import run.aquan.iron.system.model.entity.User;
import run.aquan.iron.system.service.UserService;

/**
 * @Class UserDetailsServiceImpl
 * @Description TODO
 * @Author Aquan
 * @Date 2019/12/19 16:41
 * @Version 1.0
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userService.findUserByUserName(userName);
        return new JwtUser(user);
    }

}

