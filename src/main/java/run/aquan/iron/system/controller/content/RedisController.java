package run.aquan.iron.system.controller.content;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import run.aquan.iron.system.exception.IronException;
import run.aquan.iron.system.model.params.RedisParam;
import run.aquan.iron.system.model.support.BaseResponse;
import run.aquan.iron.system.utils.JedisUtil;

import javax.validation.Valid;
import java.util.Optional;

/**
 * @Class RedisController
 * @Description TODO
 * @Author Aquan
 * @Date 2020.7.19 22:36
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/content/redis")
public class RedisController {

    @Deprecated
    @PostMapping("setKeyValus")
    @ApiOperation("Redis Set Key-Valus")
    public BaseResponse<String> setKeyValus(@Valid @RequestBody RedisParam redisParam) {
        return BaseResponse.ok(JedisUtil.setObject(redisParam.getKey(), redisParam.getValue()));
    }

    @Deprecated
    @GetMapping("getValusByKey")
    @ApiOperation("Redis Get Valus By Key")
    public BaseResponse<Object> getValusByKey(@RequestParam(value = "key") String key) {
        return BaseResponse.ok(Optional.ofNullable(JedisUtil.getObject(key)).orElseThrow(() -> new IronException("Key不存在")));
    }

}
