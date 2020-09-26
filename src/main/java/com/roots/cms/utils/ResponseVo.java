package com.roots.cms.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author admin
 * @ClassName ResponseVo.java
 * @Description TODO
 * @createTime 2020年08月05日 20:35:00
 */
@Getter
@Setter
@AllArgsConstructor
public class ResponseVo<T> {

    private Integer status;
    private Integer code;
    private String msg;
    private T data;

    public ResponseVo(Integer status, Integer code, String msg) {
        this.status = status;
        this.code = code;
        this.msg = msg;
    }

}
