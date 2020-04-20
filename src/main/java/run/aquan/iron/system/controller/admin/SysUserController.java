package run.aquan.iron.system.controller.admin;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import run.aquan.iron.security.constants.SecurityConstant;
import run.aquan.iron.security.entity.CurrentUser;
import run.aquan.iron.security.entity.JwtUser;
import run.aquan.iron.security.utils.JwtTokenUtil;
import run.aquan.iron.system.core.Result;
import run.aquan.iron.system.core.ResultResponse;
import run.aquan.iron.system.model.params.ChangePasswordParam;
import run.aquan.iron.system.model.params.LoginParam;
import run.aquan.iron.system.service.SysUserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @Class SysUserController
 * @Description TODO
 * @Author Aquan
 * @Date 2019/12/23 15:44
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/admin/users")
public class SysUserController {

    private final CurrentUser currentUser;

    private final SysUserService sysUserService;

    public SysUserController(CurrentUser currentUser, SysUserService sysUserService) {
        this.currentUser = currentUser;
        this.sysUserService = sysUserService;
    }

    @PostMapping("login")
    public Result login(@Valid @RequestBody LoginParam loginParam) {
        return sysUserService.login(loginParam);
    }

    @PostMapping("logout")
    public Result logout() {
        JwtUser currentSysUser = currentUser.getCurrentSysUser();
        return sysUserService.logout(currentSysUser);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Result getByToken(HttpServletRequest request) {
        String authorization = request.getHeader(SecurityConstant.ADMIN_TOKEN_HEADER);
        String token = authorization.replace(SecurityConstant.TOKEN_PREFIX, "");
        String date = DateUtil.formatDateTime(JwtTokenUtil.getTokenExpiration(token));
        String result = "当前访问该接口的用户为：" + currentUser.getCurrentSysUser().toString();
        return ResultResponse.genSuccessResult(result + "[" + date + "]");
    }

    @PostMapping("changePassword")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Result changePassword(@Valid @RequestBody ChangePasswordParam changePasswordParam) {
        return sysUserService.changePassword(changePasswordParam, this.currentUser.getCurrentSysUser());
    }
}
