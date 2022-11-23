package com.axfiber.dbflow.utils.exception;

import lombok.Data;

/**
 * 自定义异常
 *
 * @author Zhan Xinjian
 * @date 2022/11/23
 */
@Data
public class CommonException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    /**
     * 异常消息
     */
    private String msg;
    /**
     * 异常码
     */
    private int code = 500;

    public CommonException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public CommonException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public CommonException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public CommonException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }
}
