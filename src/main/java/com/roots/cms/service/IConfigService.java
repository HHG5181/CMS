package com.roots.cms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.roots.cms.entity.ConfigEntity;

import java.util.Map;

/**
 * @author admin
 * @Description 配置服务接口
 * @createTime 2020年08月06日 16:54:00
 */
public interface IConfigService extends IService<ConfigEntity> {

    /**
     * 查询配置集合
     * @return
     */
    public Map<String, String> selectAll();
}
