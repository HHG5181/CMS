package com.roots.cms.common.exception;

import lombok.Getter;

/**
 * @author admin
 * @ClassName BusinessException.java
 * @Description 业务异常
 * @createTime 2020年08月06日 16:46:00
 */
@Getter
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    protected final String message;

    public BusinessException(String message) {
        this.message = message;
    }

    public BusinessException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
