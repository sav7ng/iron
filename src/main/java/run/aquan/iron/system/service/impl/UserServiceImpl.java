package run.aquan.iron.system.service.impl;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.aquan.iron.system.dao.UserMapper;
import run.aquan.iron.system.exception.UserNameAlreadyExistException;
import run.aquan.iron.system.model.User;
import run.aquan.iron.system.model.params.RegisterUserParam;
import run.aquan.iron.system.service.UserService;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Optional<User> getOptional(Integer id) {
        User user = userMapper.selectById(id);
        return Optional.ofNullable(user);
    }

    @Override
    public User getById(@NotBlank Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public User findUserByUserName(String userName) {
        User user = userMapper.findUserByUserName(userName).orElseThrow(() -> new UsernameNotFoundException("No user found with username " + userName));
        return user;
    }

    @Override
    public void saveUser(RegisterUserParam registerUserParam) {
        Optional<User> optionalUser = userMapper.findUserByUserName(registerUserParam.getUserName());
        if (optionalUser.isPresent()) {
            throw new UserNameAlreadyExistException("User name already exist!Please choose another user name.");
        }
        User user = User.builder()
                .username(registerUserParam.getUserName())
                .password(bCryptPasswordEncoder.encode(registerUserParam.getPassword()))
                .roles("DEV,PM")
                .build();
        userMapper.insert(user);
    }


}
