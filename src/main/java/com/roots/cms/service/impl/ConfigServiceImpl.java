package com.roots.cms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roots.cms.common.annotation.Cache;
import com.roots.cms.dao.IConfigDao;
import com.roots.cms.entity.ConfigEntity;
import com.roots.cms.service.IConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author admin
 * @ClassName ConfigServiceImpl.java
 * @Description TODO
 * @createTime 2020年08月06日 16:55:00
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<IConfigDao, ConfigEntity> implements IConfigService {

    @Autowired
    private IConfigDao configDao;

    @Override
    @Cache
    public Map<String, String> selectAll() {
        List<ConfigEntity> configs = configDao.selectList(Wrappers.emptyWrapper());
        Map<String, String> map = new HashMap(configs.size());
        for (ConfigEntity config : configs) {
            map.put(config.getSysKey(), config.getSysValue());
        }
        return map;
    }
}
