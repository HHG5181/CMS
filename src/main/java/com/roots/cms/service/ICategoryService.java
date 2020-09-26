package com.roots.cms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.roots.cms.entity.CategoryEntity;

import java.util.List;

/**
 * @author admin
 * @Description TODO
 * @createTime 2020年08月15日 18:59:00
 */

public interface ICategoryService extends IService<CategoryEntity> {

    /**
     * 查询分类集合
     * @param categoryEntity
     * @return
     */
    public List<CategoryEntity> selectCategories(CategoryEntity categoryEntity);
}
