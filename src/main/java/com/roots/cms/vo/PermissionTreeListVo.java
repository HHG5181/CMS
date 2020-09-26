package com.roots.cms.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author admin
 * @ClassName PermissionTreeListVo.java
 * @Description TODO
 * @createTime 2020年08月06日 01:41:00
 */
@Setter
@Getter
public class PermissionTreeListVo {

    private Long permissionId;
    private String name;
    private Long parentId;
    private Boolean open = true;
    private Boolean checked = false;

}
