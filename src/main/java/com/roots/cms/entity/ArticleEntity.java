package com.roots.cms.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author admin
 * @ClassName ArticleEntity.java
 * @Description TODO
 * @createTime 2020年08月15日 17:47:00
 */
@Setter
@Getter
@Accessors(chain = true)
public class ArticleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
    private String title;
    private String userId;

    /**
     * 作者
     */
    private String author;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 封面图片
     */
    private String coverImage;

    /**
     * 分类
     */
    private Integer categoryId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 内容
     */
    private String content;


}
