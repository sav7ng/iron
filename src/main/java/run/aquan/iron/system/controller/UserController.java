package run.aquan.iron.system.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import run.aquan.iron.security.entity.CurrentUser;
import run.aquan.iron.system.core.Result;
import run.aquan.iron.system.core.ResultResponse;
import run.aquan.iron.system.model.entity.User;
import run.aquan.iron.system.model.params.RegisterUserParam;
import run.aquan.iron.system.service.UserService;

/**
* Created by CodeGenerator on 2019/08/10.
*/
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    private final CurrentUser currentUser;

    public UserController(UserService userService, CurrentUser currentUser) {
        this.userService = userService;
        this.currentUser = currentUser;
    }

    @PostMapping("register")
    @ApiOperation("Register User")
    public Result register(@RequestBody RegisterUserParam registerUserParam) {
        userService.saveUser(registerUserParam);
        return ResultResponse.genSuccessResult("注册成功");
    }

    @GetMapping("pageBy")
    public Result pageBy(@RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        Page<User> users = userService.pageBy(pageNum, pageSize);
        return ResultResponse.genSuccessResult(users);
    }

    @GetMapping("getById")
    public Result getById(@RequestParam(value = "id")Integer id) {
        User user = userService.getById(id);
        return ResultResponse.genSuccessResult(user);
    }

}
