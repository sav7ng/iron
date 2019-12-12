package run.aquan.iron.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import run.aquan.iron.core.Result;
import run.aquan.iron.model.params.WebhookParam;

import java.time.LocalDateTime;

/**
 * @Class MlxyTestController
 * @Description TODO
 * @Author Aquan
 * @Date 2019/12/12 18:08
 * @Version 1.0
 **/
@Slf4j
@RequestMapping("webhook")
@Api(value = "马来西亚支付接口回调", tags = {"马来西亚支付接口回调"})
@RestController
public class MlxyTestController {

    @PostMapping
    @ApiOperation("回调接口")
    public Result webhook(@RequestBody WebhookParam webhookParam) {
        log.warn("________________________" + LocalDateTime.now().toString() + "________________________");
        log.info(webhookParam.toString());
        log.warn("________________________________________________________________________");
        return null;

    }

}
