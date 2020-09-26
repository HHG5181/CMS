package com.roots.cms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author admin
 * @ClassName ConfigEntity.java
 * @Description 配置实体类
 * @createTime 2020年08月06日 16:50:00
 */
@Data
@TableName("config")
public class ConfigEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * key
     */
    private String sysKey;

    /**
     * value
     */
    private String sysValue;

    /**
     * 状态  1：有效 0：无效
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;
}
