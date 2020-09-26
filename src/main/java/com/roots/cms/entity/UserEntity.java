package com.roots.cms.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author admin
 * @ClassName UserEntity.java
 * @Description 用户实体类
 * @createTime 2020年08月05日 15:30:00
 */
@Setter
@Getter
@TableName("user")
public class UserEntity implements Serializable {

    /**
     * 用户Id
     */
    @TableId(value = "user_id")
    private Long userId;

    /**
     * 账号
     */
    private String userName;

    /**
     * 用户名
     */
    private String nickName;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 加密盐值
     */
    private String salt;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 头像
     */
    private String img;

    private Date createTime;

    private Date lastLoginTime;

    private Date updateTime;

    private String loginIp;

    private String remark;

    @TableField(exist = false)
    private List<RoleEntity> roles;

    public boolean isAdmin() {
        return isAdmin(this.userId);
    }

    public static boolean isAdmin(Long userId) {
        return userId == 1L;
    }

}
