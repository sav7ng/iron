package run.aquan.iron.system.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import run.aquan.iron.system.core.Result;
import run.aquan.iron.system.core.ResultResponse;

/**
 * @Class ControllerExceptionHandler
 * @Description TODO 统一异常处理
 * @Author Aquan
 * @Date 2019.12.22 0:48
 * @Version 1.0
 **/
@Slf4j
@RestControllerAdvice({"run.aquan.iron.system.controller"})
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getBindingResult().getFieldError().getDefaultMessage());
        return ResultResponse.genFailResult(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error(String.format("请求字段缺失, 类型为 %s，名称为 %s", e.getParameterType(), e.getParameterName()));
        return ResultResponse.genFailResult(String.format("请求字段缺失, 类型为 %s，名称为 %s", e.getParameterType(), e.getParameterName()));
    }

    @ExceptionHandler(IronException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleMethodIronException(IronException e) {
        log.error(String.format("系统错误：%s", e.getMessage()));
        return ResultResponse.genFailResult(String.format("系统错误：%s", e.getMessage()));
    }

}
