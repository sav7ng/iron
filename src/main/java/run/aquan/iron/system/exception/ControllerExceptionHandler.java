package run.aquan.iron.system.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getBindingResult().getFieldError().getDefaultMessage());
        return ResultResponse.genFailResult(e.getBindingResult().getFieldError().getDefaultMessage());
    }


}
