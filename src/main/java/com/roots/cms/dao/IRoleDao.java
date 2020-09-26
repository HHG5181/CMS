package com.roots.cms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.roots.cms.entity.RoleEntity;
import com.roots.cms.entity.UserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author admin
 * @Description 角色数据层Dao
 * @createTime 2020年08月05日 16:35:00
 */

public interface IRoleDao extends BaseMapper<RoleEntity> {

    /**
     * 根据用户Id查询角色集合
     * @param userId
     * @return
     */
    public Set<String> selectRoleKeyByUserId(Long userId);

    public List<RoleEntity> selectRoleEntities();

    /**
     * 根据用户Id查询角色集合
     * @param userId
     * @return
     */
    List<String> selectRolesByUserId(Long userId);


    /**
     * 分页查询角色列表
     * @param page
     * @param roleName
     * @param status
     * @param beginTime
     * @param endTime
     * @return
     */
    IPage<RoleEntity> selectRoles(@Param("page") IPage<RoleEntity> page, String roleName, Integer status, String beginTime, String endTime);

    /**
     * 角色Id下的所有用户
     * @param roleIds
     * @return
     */
    public List<UserEntity> selectUsersByRoleIds(List<Long> roleIds);

    /**
     * 查询授权权限集合
     * @param roleId
     * @return
     */
    public List<Long> selectPerms(Long roleId);
}
