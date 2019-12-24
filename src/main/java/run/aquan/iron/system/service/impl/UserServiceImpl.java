package run.aquan.iron.system.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.aquan.iron.security.entity.JwtUser;
import run.aquan.iron.security.utils.JwtTokenUtils;
import run.aquan.iron.system.core.Result;
import run.aquan.iron.system.core.ResultResponse;
import run.aquan.iron.system.enums.Datalevel;
import run.aquan.iron.system.exception.UserNameAlreadyExistException;
import run.aquan.iron.system.model.dto.AuthToken;
import run.aquan.iron.system.model.entity.User;
import run.aquan.iron.system.model.params.LoginParam;
import run.aquan.iron.system.model.params.RegisterUserParam;
import run.aquan.iron.system.repository.UserRepository;
import run.aquan.iron.system.service.UserService;

import java.util.Optional;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Result login(LoginParam loginParam) {
        String username = loginParam.getUsername();
        try {
            User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No user found with username " + username));
            boolean matches = bCryptPasswordEncoder.matches(loginParam.getPassword(), user.getPassword());
            if (matches) {
                AuthToken authToken = JwtTokenUtils.createToken(new JwtUser(user), loginParam.getRememberMe());
                return ResultResponse.genSuccessResult(authToken);
            } else {
                return ResultResponse.genFailResult("Password erro");
            }
        } catch (UsernameNotFoundException e) {
            log.error(e.getMessage());
            return ResultResponse.genFailResult("No user found with username " + username);
        }
    }

    @Override
    public User findUserByUserName(String userName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new UsernameNotFoundException("No user found with username " + userName));
        return user;
    }

    @Override
    public Result saveUser(RegisterUserParam registerUserParam) {
        Optional<User> optionalUser = userRepository.findByUsername(registerUserParam.getUsername());
        try {
            if (optionalUser.isPresent()) {
                throw new UserNameAlreadyExistException("User name already exist!Please choose another user name.");
            }
            User user = User.builder()
                    .username(registerUserParam.getUsername())
                    .password(bCryptPasswordEncoder.encode(registerUserParam.getPassword()))
                    .roles("USER")
                    .build();
            User save = userRepository.save(user);
            return ResultResponse.genSuccessResult(save);
        } catch (UserNameAlreadyExistException e) {
            log.error(e.getMessage());
            return ResultResponse.genFailResult(e.getMessage());
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
