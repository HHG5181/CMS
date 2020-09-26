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
 * @ClassName PermissionEntity.java
 * @Description 权限实体类
 * @createTime 2020年08月05日 15:38:00
 */
@Setter
@Getter
@TableName("permission")
public class PermissionEntity implements Serializable {

    private static final Long serialVersionUID = 1L;

    /**
     * 权限Id
     */
    @TableId(value = "permission_id")
    private Long permissionId;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限描述
     */
    private String description;

    /**
     * 权限访问路径
     */
    private String url;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 父级权限id
     */
    private Long parentId;

    /**
     * 类型   0：目录   1：菜单   2：按钮
     */
    private Integer type;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * 图标
     */
    private String icon;

    /**
     * 状态：1有效; 0无效
     */
    private Integer status;

    private Date createTime;

    private Date updateTime;

    @TableField(exist = false)
    private List<PermissionEntity> children;

    @TableField(exist = false)
    private List<RoleEntity> roles;
}
