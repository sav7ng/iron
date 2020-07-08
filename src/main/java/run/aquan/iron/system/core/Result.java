package run.aquan.iron.system.core;

import com.alibaba.fastjson.JSON;
import run.aquan.iron.system.enums.ResultCode;

/**
 * @Class Result
 * @Description TODO 统一API响应结果封装
 * @Author Aquan
 * @Date 2019/8/16 14:54
 * @Version 1.0
 **/
public class Result<T> {
    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public Result<T> setCode(ResultCode resultCode) {
        this.code = resultCode.code();
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}

