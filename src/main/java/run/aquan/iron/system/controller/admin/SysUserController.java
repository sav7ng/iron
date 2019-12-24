package run.aquan.iron.system.controller.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import run.aquan.iron.security.entity.CurrentUser;
import run.aquan.iron.system.core.Result;
import run.aquan.iron.system.core.ResultResponse;
import run.aquan.iron.system.model.params.LoginParam;
import run.aquan.iron.system.service.SysUserService;

import javax.validation.Valid;

/**
 * @Class SysUserController
 * @Description TODO
 * @Author Aquan
 * @Date 2019/12/23 15:44
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/admin/user")
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

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Result getByToken() {
        String result = "当前访问该接口的用户为：" + currentUser.getCurrentSysUser().toString();
        return ResultResponse.genSuccessResult(result);
    }

}
