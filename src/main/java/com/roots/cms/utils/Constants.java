package com.roots.cms.utils;

/**
 * @author admin
 * @ClassName Constants.java
 * @Description 常量
 * @createTime 2020年08月05日 16:09:00
 */

public class Constants {

    //默认密码
    public static final String PASSWORD = "1234";

    public static final Integer SUCCESS_STATUS = 200;
    public static final Integer FAIL_STATUS = 500;

    public static final Integer SUCCESS_CODE = 1;
    public static final Integer FAIL_CODE = 0;

    public static final Integer STATUS_VALID = 0;
    public static final Integer STATUS_INVALID = 1;

    public static final Integer TOP_MENU_ID = 0;
    public static final String TOP_MENU_NAME = "顶层菜单";

    public static final String SHIRO_REDIS_SESSION_PREFIX = "cms:session:";
    public static final String SHIRO_REDIS_CACHE_NAME = "shiro_cms";
}
