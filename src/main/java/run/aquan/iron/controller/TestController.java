package run.aquan.iron.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.aquan.iron.core.Result;
import run.aquan.iron.core.ResultGenerator;

/**
 * @Class TestController
 * @Description TODO
 * @Author Aquan
 * @Date 2019.9.25 1:09
 * @Version 1.0
 **/
@RestController
@RequestMapping("test")
public class TestController {

    @ApiOperation("Test")
    @GetMapping
    public Result test() {
        return ResultGenerator.genSuccessResult("请求成功！");
    }

}
