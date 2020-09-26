package com.roots.cms.utils;

import java.util.List;

/**
 * @author admin
 * @ClassName ResultUtils.java
 * @Description 返回结果封装类
 * @createTime 2020年08月05日 20:34:00
 */

public class ResultUtils {
    public static ResponseVo success() {
        return vo(Constants.SUCCESS_STATUS, Constants.SUCCESS_CODE, null, null);
    }

    public static ResponseVo success(String msg) {
        return vo(Constants.SUCCESS_STATUS, Constants.SUCCESS_CODE, msg,null);
    }

    public static ResponseVo success(String msg, Object data) {
        return vo(Constants.SUCCESS_STATUS, Constants.SUCCESS_CODE, msg, data);
    }

    public static ResponseVo error() {
        return vo(Constants.FAIL_STATUS, Constants.FAIL_CODE, null, null);
    }

    public static ResponseVo error( String msg) {
        return vo(Constants.FAIL_STATUS, Constants.FAIL_CODE,  msg, null);
    }

    public static ResponseVo error(String msg, Object data) {
        return vo(Constants.FAIL_STATUS, Constants.FAIL_CODE, msg, data);
    }

    public static PageResultVo table(List<?> list, Long total) {
        return new PageResultVo(list, total);
    }

    public static ResponseVo vo(Integer status, Integer code, String message, Object data) {
        return new ResponseVo<>(status, code, message, data);
    }
}
