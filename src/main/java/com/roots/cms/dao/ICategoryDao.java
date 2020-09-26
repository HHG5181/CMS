package com.roots.cms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roots.cms.entity.CategoryEntity;

import java.util.List;

/**
 * @author admin
 * @Description TODO
 * @createTime 2020年08月15日 19:00:00
 */

public interface ICategoryDao extends BaseMapper<CategoryEntity> {

    /**
     * 查询分类集合
     * @param categoryEntity
     * @return
     */
    public List<CategoryEntity> selectCategories(CategoryEntity categoryEntity);
}
