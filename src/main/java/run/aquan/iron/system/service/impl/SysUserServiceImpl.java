package run.aquan.iron.system.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import run.aquan.iron.security.entity.JwtUser;
import run.aquan.iron.security.utils.JwtTokenUtil;
import run.aquan.iron.system.constants.IronConstant;
import run.aquan.iron.system.enums.Datalevel;
import run.aquan.iron.system.exception.IronException;
import run.aquan.iron.system.model.dto.AuthToken;
import run.aquan.iron.system.model.entity.SysUser;
import run.aquan.iron.system.model.params.ChangePasswordParam;
import run.aquan.iron.system.model.params.LoginParam;
import run.aquan.iron.system.repository.SysUserRepository;
import run.aquan.iron.system.service.SysUserService;
import run.aquan.iron.system.utils.JedisUtils;

import java.util.Date;
import java.util.Optional;

/**
 * @Class SysUserServiceImpl
 * @Description TODO
 * @Author Aquan
 * @Date 2019.12.23 23:44
 * @Version 1.0
 **/
@Slf4j
@Service
public class SysUserServiceImpl implements SysUserService {

    private final SysUserRepository sysUserRepository;

    @Autowired
    public SysUserServiceImpl(SysUserRepository sysUserRepository) {
        this.sysUserRepository = sysUserRepository;
    }

    @Override
    public String init() {
        // 防止重复初始化
        Optional<SysUser> admin = sysUserRepository.findByUsernameAndDatalevel("admin", Datalevel.EFFECTIVE);
        if (admin.isPresent()) {
            return "No need to repeat initialization";
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        SysUser sysUser = SysUser.builder().username("admin").password(bCryptPasswordEncoder.encode("aquan")).roles("ADMIN").build();
        try {
            sysUserRepository.save(sysUser);
            return "initialized successfully";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "initialization failed";
        }
    }

    @Override
    public SysUser findUserByUserName(String username) {
        try {
            SysUser sysUser = sysUserRepository.findByUsernameAndDatalevel(username, Datalevel.EFFECTIVE).orElseThrow(() -> new UsernameNotFoundException("No user found with username " + username));
            return sysUser;
        } catch (UsernameNotFoundException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public AuthToken login(LoginParam loginParam) {
        String username = loginParam.getUsername();
        try {
            SysUser sysUser = sysUserRepository.findByUsernameAndDatalevel(username, Datalevel.EFFECTIVE).orElseThrow(() -> new UsernameNotFoundException("No user found with username " + username));
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            if (!bCryptPasswordEncoder.matches(loginParam.getPassword(), sysUser.getPassword()))
                throw new IronException("Admin Password erro");
            synchronized (this) {
                AuthToken authToken = JwtTokenUtil.createToken(new JwtUser(sysUser));
                sysUser.setExpirationTime(authToken.getExpiration());
                sysUserRepository.saveAndFlush(sysUser);
                return authToken;
            }
        } catch (UsernameNotFoundException e) {
            log.error(e.getMessage());
            throw new IronException("No user found with username " + username);
        }
    }

    @Override
    public String logout(JwtUser currentSysUser) {
        String username = currentSysUser.getUsername();
        try {
            SysUser sysUser = sysUserRepository.findByUsernameAndDatalevel(username, Datalevel.EFFECTIVE).orElseThrow(() -> new UsernameNotFoundException("No user found with username " + username));
            sysUser.setExpirationTime(new Date());
            sysUserRepository.saveAndFlush(sysUser);
            JedisUtils.delKey(IronConstant.REDIS_REFRESHTOKEN_PREFIX + sysUser.getUsername());
            return "成功退出";
        } catch (UsernameNotFoundException e) {
            log.error(e.getMessage());
            throw new IronException(e.getMessage());
        }
    }

    @Override
    public String changePassword(ChangePasswordParam changePasswordParam, JwtUser currentSysUser) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (!bCryptPasswordEncoder.matches(changePasswordParam.getPassword(), currentSysUser.getPassword())) {
            throw new IronException("原密码错误");
        }
        try {
            String newPassword = bCryptPasswordEncoder.encode(changePasswordParam.getNewPassword());
            SysUser sysUser = sysUserRepository.findByUsernameAndDatalevel(currentSysUser.getUsername(), Datalevel.EFFECTIVE).orElseThrow(() -> new UsernameNotFoundException("No user found with username " + currentSysUser.getUsername()));
            sysUser.setPassword(newPassword);
            sysUserRepository.saveAndFlush(sysUser);
            return "修改成功";
        } catch (UsernameNotFoundException e) {
            log.error(e.getMessage());
            throw new IronException(e.getMessage());
        }
    }

}
