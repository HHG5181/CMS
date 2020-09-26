package com.roots.cms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author admin
 * @ClassName UserRole.java
 * @Description 用户角色
 * @createTime 2020年08月05日 15:40:00
 */

@Setter
@Getter
@TableName("user_role")
public class UserRole implements Serializable {

    private Long id;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 角色Id
     */
    private Long roleId;
}
