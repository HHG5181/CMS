package com.roots.cms.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author admin
 * @ClassName Ztree.java
 * @Description 权限树结构实体类
 * @createTime 2020年08月18日 16:31:00
 */
@Data
public class Ztree implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 节点ID */
    private Long id;

    /** 节点父ID */
    private Long pId;

    /** 节点标题 */
    private String title;

    /** 是否勾选 */
    private boolean checked = false;

    private List<Ztree> _sub_;

}
