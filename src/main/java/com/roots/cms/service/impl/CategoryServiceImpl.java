package com.roots.cms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roots.cms.dao.ICategoryDao;
import com.roots.cms.entity.CategoryEntity;
import com.roots.cms.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author admin
 * @ClassName CategoryServiceImpl.java
 * @Description TODO
 * @createTime 2020年08月15日 19:00:00
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<ICategoryDao, CategoryEntity> implements ICategoryService {

    @Autowired
    private ICategoryDao categoryDao;

    @Override
    public List<CategoryEntity> selectCategories(CategoryEntity categoryEntity) {
        return categoryDao.selectCategories(categoryEntity);
    }
}
