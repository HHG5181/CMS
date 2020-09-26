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
 * @ClassName RoleEntity.java
 * @Description 角色实体类
 * @createTime 2020年08月05日 15:35:00
 */
@Setter
@Getter
@TableName("role")
public class RoleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "role_id")
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色权限
     */
    private String roleKey;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态：0有效；1无效
     */
    private Integer status;

    private Date createTime;

    private Date updateTime;

    @TableField(exist = false)
    private List<UserEntity> users;

    @TableField(exist = false)
    private List<PermissionEntity> permissions;
}
