package run.aquan.iron.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import run.aquan.iron.system.core.Result;
import run.aquan.iron.system.core.ResultResponse;
import run.aquan.iron.system.model.User;
import run.aquan.iron.system.service.UserService;

import javax.annotation.Resource;
import java.util.Optional;

/**
* Created by CodeGenerator on 2019/08/10.
*/
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

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
