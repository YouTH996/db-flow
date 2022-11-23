package com.axfiber.dbflow.utils.exception;

import com.axfiber.dbflow.utils.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Zhan Xinjian
 * @date 2022/11/23
 */
@RestControllerAdvice
public class CommonExceptionHandler {
    /**
     * 处理自定义异常
     */
    @ExceptionHandler(CommonException.class)
    public R handleCommonException(CommonException e){
        R r = new R();
        r.put("code", e.getCode());
        r.put("msg", e.getMessage());
        return r;
    }
}
