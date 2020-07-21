package run.aquan.iron.system.controller.content;

import cn.hutool.core.date.DateUtil;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import run.aquan.iron.security.constants.SecurityConstant;
import run.aquan.iron.security.entity.CurrentUser;
import run.aquan.iron.security.utils.JwtTokenUtil;
import run.aquan.iron.security.token.AuthToken;
import run.aquan.iron.system.model.entity.User;
import run.aquan.iron.system.model.params.ChangePasswordParam;
import run.aquan.iron.system.model.params.LoginParam;
import run.aquan.iron.system.model.params.RefreshTokenParam;
import run.aquan.iron.system.model.params.RegisterUserParam;
import run.aquan.iron.system.model.support.BaseResponse;
import run.aquan.iron.system.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by CodeGenerator on 2019/08/10.
 */
@RestController
@RequestMapping("/api/content/users")
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

    @PostMapping("logout")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public BaseResponse<String> logout() {
        return BaseResponse.ok(userService.logout(this.currentUser.getCurrentUser()));
    }

    @PostMapping("register")
    @ApiOperation("Register User")
    public Integer register(@Valid @RequestBody RegisterUserParam registerUserParam) {
        return userService.saveUser(registerUserParam);
    }

    @PostMapping("refreshToken")
    @ApiOperation("Refreshes Token")
    public AuthToken refreshToken(@Valid @RequestBody RefreshTokenParam refreshTokenParam) {
        return userService.refreshToken(refreshTokenParam.getRefreshToken());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public BaseResponse<String> getByToken(HttpServletRequest request) {
        String authorization = request.getHeader(SecurityConstant.TOKEN_HEADER);
        String token = authorization.replace(SecurityConstant.TOKEN_PREFIX, "");
        String date = DateUtil.formatDateTime(JwtTokenUtil.getTokenExpiration(token));
        return BaseResponse.ok("当前访问该接口的用户为：" + currentUser.getCurrentUser().toString() + "[" + date + "]");
    }

    @GetMapping("getById")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public User getById(@RequestParam(value = "id") Integer id) {
        return userService.getById(id);
    }

    @PostMapping("changePassword")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public BaseResponse<String> changePassword(@Valid @RequestBody ChangePasswordParam changePasswordParam) {
        return BaseResponse.ok(userService.changePassword(changePasswordParam, this.currentUser.getCurrentUser()));
    }

    @GetMapping("getMybatis")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public List<User> getMybatis() {
        return userService.mybaisPlusGetUser();
    }

}
