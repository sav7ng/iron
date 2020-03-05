package run.aquan.iron.system.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.aquan.iron.system.core.Result;
import run.aquan.iron.system.core.ResultResponse;

/**
 * @Class ErrorController
 * @Description TODO
 * @Author Aquan
 * @Date 2020/3/4 18:09
 * @Version 1.0
 **/
@Api(tags = "统一错误处理模块")
@RestController
@RequestMapping("/api/error")
public class ErrorController {

    @GetMapping
    public Result error() {
        return ResultResponse.genUnauthorizedResult("Full authentication is required to access this resource");
    }

}
