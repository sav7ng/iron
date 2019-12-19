package run.aquan.iron.system.controller;

import org.springframework.web.bind.annotation.*;
import run.aquan.iron.system.core.Result;
import run.aquan.iron.system.core.ResultResponse;
import run.aquan.iron.system.model.User;
import run.aquan.iron.system.model.params.RegisterUserParam;
import run.aquan.iron.system.service.UserService;

import javax.annotation.Resource;
import java.util.Optional;

/**
* Created by CodeGenerator on 2019/08/10.
*/
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("registerUser")
    public Result registerUser(@RequestBody RegisterUserParam registerUserParam) {
        userService.saveUser(registerUserParam);
        return ResultResponse.genSuccessResult("注册成功");
    }

    @GetMapping("getBy")
    public Result getBy(@RequestParam(value = "id")Integer id) {
        Optional<User> optional = userService.getOptional(id);
        return ResultResponse.genSuccessResult(optional);
    }

    @GetMapping("getById")
    public Result getById(@RequestParam(value = "id")Integer id) {
        User user = userService.getById(id);
        return ResultResponse.genSuccessResult(user);
    }

}
