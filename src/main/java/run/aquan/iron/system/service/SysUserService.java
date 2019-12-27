package run.aquan.iron.system.service;

import run.aquan.iron.security.entity.JwtUser;
import run.aquan.iron.system.core.Result;
import run.aquan.iron.system.model.entity.SysUser;
import run.aquan.iron.system.model.params.LoginParam;

public interface SysUserService {

    String init();

    SysUser findUserByUserName(String username);

    Result login(LoginParam loginParam);

    Result logout(JwtUser currentSysUser);
}
