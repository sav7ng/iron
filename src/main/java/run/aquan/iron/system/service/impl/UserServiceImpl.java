package run.aquan.iron.system.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.aquan.iron.security.entity.JwtUser;
import run.aquan.iron.security.utils.JwtTokenUtil;
import run.aquan.iron.system.enums.Datalevel;
import run.aquan.iron.system.exception.IronException;
import run.aquan.iron.system.exception.UserNameAlreadyExistException;
import run.aquan.iron.system.model.dto.AuthToken;
import run.aquan.iron.system.model.entity.User;
import run.aquan.iron.system.model.params.ChangePasswordParam;
import run.aquan.iron.system.model.params.LoginParam;
import run.aquan.iron.system.model.params.RegisterUserParam;
import run.aquan.iron.system.repository.UserRepository;
import run.aquan.iron.system.service.UserService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AuthToken login(LoginParam loginParam) {
        String username = loginParam.getUsername();
        try {
            User user = userRepository.findByUsernameAndDatalevel(username, Datalevel.EFFECTIVE).orElseThrow(() -> new UsernameNotFoundException("No user found with username " + username));
            if (bCryptPasswordEncoder.matches(loginParam.getPassword(), user.getPassword())) {
                synchronized (this) {
                    AuthToken authToken = JwtTokenUtil.createToken(new JwtUser(user), loginParam.getRememberMe());
                    user.setExpirationTime(authToken.getExpiration());
                    userRepository.saveAndFlush(user);
                    return authToken;
                }
            } else {
                throw new IronException("User Password erro");
            }
        } catch (UsernameNotFoundException e) {
            log.error(e.getMessage());
            throw new IronException("No user found with username " + username);
        }
    }

    @Override
    public String logout(JwtUser currentUser) {
        String username = currentUser.getUsername();
        try {
            User user = userRepository.findByUsernameAndDatalevel(username, Datalevel.EFFECTIVE).orElseThrow(() -> new UsernameNotFoundException("No user found with username " + username));
            user.setExpirationTime(new Date());
            userRepository.saveAndFlush(user);
            return "成功退出";
        } catch (UsernameNotFoundException e) {
            log.error(e.getMessage());
            throw new IronException(e.getMessage());
        }
    }

    @Override
    public String changePassword(ChangePasswordParam changePasswordParam, JwtUser currentUser) {
        if (!bCryptPasswordEncoder.matches(changePasswordParam.getPassword(), currentUser.getPassword()))
            throw new IronException("原密码错误");
        try {
            String newPassword = bCryptPasswordEncoder.encode(changePasswordParam.getNewPassword());
            User user = userRepository.findByUsernameAndDatalevel(currentUser.getUsername(), Datalevel.EFFECTIVE).orElseThrow(() -> new UsernameNotFoundException("No user found with username " + currentUser.getUsername()));
            user.setPassword(newPassword);
            userRepository.saveAndFlush(user);
            return "修改成功";
        } catch (UsernameNotFoundException e) {
            log.error(e.getMessage());
            throw new IronException(e.getMessage());
        }
    }

    @Override
    public User findUserByUserName(String username) {
        try {
            User user = userRepository.findByUsernameAndDatalevel(username, Datalevel.EFFECTIVE).orElseThrow(() -> new UsernameNotFoundException("No user found with username " + username));
            return user;
        } catch (UsernameNotFoundException e) {
            log.error(e.getMessage());
            throw new IronException(e.getMessage());
        }
    }

    @Override
    public User saveUser(RegisterUserParam registerUserParam) {
        Optional<User> optionalUser = userRepository.findByUsernameAndDatalevel(registerUserParam.getUsername(), Datalevel.EFFECTIVE);
        try {
            if (optionalUser.isPresent())
                throw new UserNameAlreadyExistException("User name already exist!Please choose another user name.");
            User user = User.builder()
                    .username(registerUserParam.getUsername())
                    .password(bCryptPasswordEncoder.encode(registerUserParam.getPassword()))
                    .roles("USER")
                    .build();
            User save = userRepository.save(user);
            return save;
        } catch (UserNameAlreadyExistException e) {
            log.error(e.getMessage());
            throw new IronException(e.getMessage());
        }
    }

    @Override
    public User getById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("No user found with Id " + id));
        return user;
    }

    @Override
    public Page<User> pageBy(Pageable pageable) {
        Page<User> users = userRepository.findAllByDatalevel(Datalevel.EFFECTIVE, pageable);
        return users;
    }


}
