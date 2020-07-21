package run.aquan.iron.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.aquan.iron.security.constants.SecurityConstant;
import run.aquan.iron.security.entity.JwtUser;
import run.aquan.iron.security.utils.JwtTokenUtil;
import run.aquan.iron.system.constants.IronConstant;
import run.aquan.iron.system.enums.Datalevel;
import run.aquan.iron.system.exception.IronException;
import run.aquan.iron.system.exception.UserNameAlreadyExistException;
import run.aquan.iron.system.mapper.UserMapper;
import run.aquan.iron.security.token.AuthToken;
import run.aquan.iron.system.model.entity.User;
import run.aquan.iron.system.model.params.ChangePasswordParam;
import run.aquan.iron.system.model.params.LoginParam;
import run.aquan.iron.system.model.params.RegisterUserParam;
import run.aquan.iron.system.service.UserService;
import run.aquan.iron.system.utils.JedisUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public AuthToken login(LoginParam loginParam) {
        String username = loginParam.getUsername();
        try {
            User user = userMapper.findByUsernameAndDatalevel(username, Datalevel.EFFECTIVE).orElseThrow(() -> new UsernameNotFoundException("No user found with username " + username));
            if (bCryptPasswordEncoder.matches(loginParam.getPassword(), user.getPassword())) {
                synchronized (this) {
                    AuthToken authToken = JwtTokenUtil.createToken(new JwtUser(user));
                    user.setExpirationTime(authToken.getExpiration());
                    userMapper.updateById(user);
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
            User user = userMapper.findByUsernameAndDatalevel(username, Datalevel.EFFECTIVE).orElseThrow(() -> new UsernameNotFoundException("No user found with username " + username));
            user.setExpirationTime(new Date());
            userMapper.updateById(user);
            JedisUtil.delKey(IronConstant.REDIS_REFRESHTOKEN_PREFIX + user.getUsername());
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
            User user = userMapper.findByUsernameAndDatalevel(currentUser.getUsername(), Datalevel.EFFECTIVE).orElseThrow(() -> new UsernameNotFoundException("No user found with username " + currentUser.getUsername()));
            user.setPassword(newPassword);
            userMapper.updateById(user);
            return "修改成功";
        } catch (UsernameNotFoundException e) {
            log.error(e.getMessage());
            throw new IronException(e.getMessage());
        }
    }

    @Override
    public User findUserByUserName(String username) {
        try {
            return userMapper.findByUsernameAndDatalevel(username, Datalevel.EFFECTIVE).orElseThrow(() -> new UsernameNotFoundException("No user found with username " + username));
        } catch (UsernameNotFoundException e) {
            log.error(e.getMessage());
            throw new IronException(e.getMessage());
        }
    }

    @Override
    public Integer saveUser(RegisterUserParam registerUserParam) {
            Optional<User> optionalUser = userMapper.findByUsernameAndDatalevel(registerUserParam.getUsername(), Datalevel.EFFECTIVE);
        try {
            if (optionalUser.isPresent())
                throw new UserNameAlreadyExistException("User name already exist!Please choose another user name.");
            User user = User.builder()
                    .username(registerUserParam.getUsername())
                    .password(bCryptPasswordEncoder.encode(registerUserParam.getPassword()))
                    .roles("USER")
                    .build();
            return userMapper.insert(user);
        } catch (UserNameAlreadyExistException e) {
            log.error(e.getMessage());
            throw new IronException(e.getMessage());
        }
    }

    @Override
    public User getById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<User> mybaisPlusGetUser() {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("datalevel", Datalevel.EFFECTIVE);
        return userMapper.selectList(queryWrapper);
    }

    @Override
    public AuthToken refreshToken(String refreshToken) {
        Claims refreshTokenBody = JwtTokenUtil.getRefreshTokenBody(refreshToken);
        Long issuedTime = refreshTokenBody.getIssuedAt().getTime();
        String username = refreshTokenBody.getSubject();
        String json = (String) Optional.ofNullable(JedisUtil.getObject(IronConstant.REDIS_REFRESHTOKEN_PREFIX + username)).orElseThrow(() -> new AccessDeniedException("refreshToken 无效"));
        if (StringUtils.isBlank(json) || !json.equals(refreshToken)) {
            throw new AccessDeniedException("refreshToken 无效");
        }
        User user = userMapper.findByUsernameAndDatalevel(username, Datalevel.EFFECTIVE).orElseThrow(() -> new AccessDeniedException("refreshToken 无效"));
        Long loginTime = user.getExpirationTime().getTime() - SecurityConstant.EXPIRATION * 1000;
        if (!issuedTime.equals(loginTime)) {
            throw new AccessDeniedException("refreshToken 无效");
        }
        synchronized (this) {
            AuthToken authToken = JwtTokenUtil.createToken(new JwtUser(user));
            user.setExpirationTime(authToken.getExpiration());
            userMapper.updateById(user);
            return authToken;
        }
    }

}
