package run.aquan.iron.system.service;

import run.aquan.iron.security.entity.JwtUser;
import run.aquan.iron.security.token.AuthToken;
import run.aquan.iron.system.model.entity.SysUser;
import run.aquan.iron.system.model.params.ChangePasswordParam;
import run.aquan.iron.system.model.params.LoginParam;

public interface SysUserService {

    String init();

    SysUser findUserByUserName(String username);

    AuthToken login(LoginParam loginParam);

    String logout(JwtUser currentSysUser);

    String changePassword(ChangePasswordParam changePasswordParam, JwtUser currentSysUser);

}
