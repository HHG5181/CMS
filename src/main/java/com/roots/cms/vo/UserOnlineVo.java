package com.roots.cms.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author admin
 * @ClassName OnlineVo.java
 * @Description TODO
 * @createTime 2020年08月08日 15:14:00
 */
@Setter
@Getter
public class UserOnlineVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Session Id
     */
    private String sessionId;

    private String username;

    /**
     * ip地址
     */
    private String ipAddr;

    /**
     * Session创建时间
     */
    private Date startTime;

    /**
     * Session最后交互时间
     */
    private Date lastAccess;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * timeout
     */
    private long timeout;

    /**
     * 是否踢出
     */
    private boolean sessionStatus = Boolean.TRUE;
}
