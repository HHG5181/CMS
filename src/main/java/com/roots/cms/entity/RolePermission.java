package com.roots.cms.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author admin
 * @ClassName RolePermission.java
 * @Description 角色权限
 * @createTime 2020年08月05日 15:41:00
 */

@Setter
@Getter
public class RolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 角色Id
     */
    private Long roleId;

    /**
     * 权限Id
     */
    private Long permissionId;
}
