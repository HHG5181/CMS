package com.roots.cms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roots.cms.entity.UserRole;

/**
 * @author admin
 * @Description 用户角色数据层
 * @createTime 2020年08月05日 16:38:00
 */
public interface IUserRoleDao extends BaseMapper<UserRole> {

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    public int countUserRoleByRoleId(Long roleId);
}
