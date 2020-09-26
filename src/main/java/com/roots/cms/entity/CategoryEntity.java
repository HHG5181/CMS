package com.roots.cms.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author admin
 * @ClassName CategoryEntity.java
 * @Description 文章分类
 * @createTime 2020年08月15日 18:55:00
 */
@Data
public class CategoryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer pid;
    private String name;
    private String description;
    private Integer sort;
    private Integer status;
    private String icon;
    private Date createTime;
    private Date updateTime;

    @TableField(exist = false)
    private CategoryEntity parent;
    @TableField(exist = false)
    private List<CategoryEntity> children;
}
