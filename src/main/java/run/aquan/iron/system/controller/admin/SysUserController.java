package run.aquan.iron.system.controller.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.aquan.iron.system.core.Result;
import run.aquan.iron.system.core.ResultResponse;

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

    @GetMapping("test")
    public Result getByToken() {
        String result = "拦截器";
        return ResultResponse.genSuccessResult(result);
    }

}
