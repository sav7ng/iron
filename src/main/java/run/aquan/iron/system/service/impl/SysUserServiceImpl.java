package run.aquan.iron.system.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import run.aquan.iron.system.model.entity.SysUser;
import run.aquan.iron.system.repository.SysUserRepository;
import run.aquan.iron.system.service.SysUserService;

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

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SysUserServiceImpl(SysUserRepository sysUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.sysUserRepository = sysUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Boolean init() {
        SysUser sysUser = SysUser.builder().username("admin").password(bCryptPasswordEncoder.encode("aquan")).roles("ADMIN").build();
        try {
            sysUserRepository.save(sysUser);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

}
