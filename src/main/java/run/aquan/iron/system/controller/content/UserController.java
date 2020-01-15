package run.aquan.iron.system.controller.content;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import run.aquan.iron.security.constants.SecurityConstant;
import run.aquan.iron.security.entity.CurrentUser;
import run.aquan.iron.security.entity.JwtUser;
import run.aquan.iron.security.utils.JwtTokenUtil;
import run.aquan.iron.system.core.Result;
import run.aquan.iron.system.core.ResultResponse;
import run.aquan.iron.system.model.entity.User;
import run.aquan.iron.system.model.params.LoginParam;
import run.aquan.iron.system.model.params.RegisterUserParam;
import run.aquan.iron.system.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.data.domain.Sort.Direction.DESC;

/**
* Created by CodeGenerator on 2019/08/10.
*/
@RestController
@RequestMapping("/api/content/users")
public class UserController {

    private final UserService userService;

    private final CurrentUser currentUser;

    public UserController(UserService userService, CurrentUser currentUser) {
        this.userService = userService;
        this.currentUser = currentUser;
    }

    @PostMapping("login")
    public Result login(@Valid @RequestBody LoginParam loginParam) {
        return userService.login(loginParam);
    }

    @PostMapping("logout")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public Result logout() {
        return userService.logout(this.currentUser.getCurrentUser());
    }

    @PostMapping("register")
    @ApiOperation("Register User")
    public Result register(@Valid @RequestBody RegisterUserParam registerUserParam) {
        return userService.saveUser(registerUserParam);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public Result getByToken(HttpServletRequest request) {
        String authorization = request.getHeader(SecurityConstant.TOKEN_HEADER);
        String token = authorization.replace(SecurityConstant.TOKEN_PREFIX, "");
        String date = DateUtil.formatDateTime(JwtTokenUtil.getTokenExpiration(token));
        String result = "当前访问该接口的用户为：" + currentUser.getCurrentUser().toString();
        return ResultResponse.genSuccessResult(result + "[" + date + "]");
    }


    @GetMapping("pageBy")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public Result pageBy(@PageableDefault(sort = "updateTime", direction = DESC) Pageable pageable) {
        Page<User> users = userService.pageBy(pageable);
        return ResultResponse.genSuccessResult(users);
    }

    @GetMapping("getById")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public Result getById(@RequestParam(value = "id") Integer id) {
        User user = userService.getById(id);
        return ResultResponse.genSuccessResult(user);
    }

}
