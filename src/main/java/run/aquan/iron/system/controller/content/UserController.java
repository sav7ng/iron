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
import run.aquan.iron.security.utils.JwtTokenUtil;
import run.aquan.iron.system.model.dto.AuthToken;
import run.aquan.iron.system.model.entity.User;
import run.aquan.iron.system.model.params.ChangePasswordParam;
import run.aquan.iron.system.model.params.LoginParam;
import run.aquan.iron.system.model.params.RefreshTokenParam;
import run.aquan.iron.system.model.params.RegisterUserParam;
import run.aquan.iron.system.model.support.BaseResponse;
import run.aquan.iron.system.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.data.domain.Sort.Direction.DESC;

/**
* Created by CodeGenerator on 2019/08/10.
*/
@RestController
@RequestMapping("/api/content/user")
public class UserController {

    private final CurrentUser currentUser;

    private final UserService userService;

    public UserController(CurrentUser currentUser, UserService userService) {
        this.currentUser = currentUser;
        this.userService = userService;
    }

    @PostMapping("login")
    public AuthToken login(@Valid @RequestBody LoginParam loginParam) {
        return userService.login(loginParam);
    }

    @PostMapping("refresh/token")
    @ApiOperation("Refreshes Token")
    public AuthToken refreshToken(@Valid @RequestBody RefreshTokenParam refreshTokenParam) {
        return userService.refreshToken(refreshTokenParam.getRefreshToken());
    }

    @PostMapping("logout")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public String logout() {
        return userService.logout(this.currentUser.getCurrentUser());
    }

    @PostMapping("register")
    @ApiOperation("Register User")
    public BaseResponse<String> register(@Valid @RequestBody RegisterUserParam registerUserParam) {
        return BaseResponse.ok(userService.saveUser(registerUserParam));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String getByToken(HttpServletRequest request) {
        String authorization = request.getHeader(SecurityConstant.CONTEN_TOKEN_HEADER);
        String token = authorization.replace(SecurityConstant.TOKEN_PREFIX, "");
        String date = DateUtil.formatDateTime(JwtTokenUtil.getTokenExpiration(token));
        String result = "当前访问该接口的用户为：" + currentUser.getCurrentUser().toString() + "[" + date + "]";
        return result;
    }

    @GetMapping("pageBy")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public Page<User> pageBy(@PageableDefault(sort = "updatedTime", direction = DESC) Pageable pageable) {
        return userService.pageBy(pageable);
    }

    @GetMapping("getById")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public User getById(@RequestParam(value = "userId") String userId) {
        return userService.getById(userId);
    }

    @PostMapping("changePassword")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public String changePassword(@Valid @RequestBody ChangePasswordParam changePasswordParam) {
        return userService.changePassword(changePasswordParam, this.currentUser.getCurrentUser());
    }

}
