package run.aquan.iron.system.core;

import run.aquan.iron.system.enums.ResultCode;

/**
 * @Class ResultResponse
 * @Description TODO 响应结果生成工具
 * @Author Aquan
 * @Date 2019/8/16 15:01
 * @Version 1.0
 **/
public class ResultResponse {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static Result genSuccessResult() {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static <T> Result<T> genSuccessResult(T data) {
        return genSuccessResult().setData(data);
    }

    public static Result genFailResult(String message) {
        return new Result()
                .setCode(ResultCode.FAIL)
                .setMessage(message);
    }


    public static Result genUnauthorizedResult(String message) {
        return new Result()
                .setCode(ResultCode.UNAUTHORIZED)
                .setMessage(message);
    }

}

