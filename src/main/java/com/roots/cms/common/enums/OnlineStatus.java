package com.roots.cms.common.enums;

/**
 * @author admin
 * @Description 用户会话
 * @createTime 2020年08月08日 14:44:00
 */

public enum OnlineStatus {
    on_line("在线"),
    off_line("离线");

    private final String info;

    private OnlineStatus(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
