package com.roots.cms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.roots.cms.entity.PermissionEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author admin
 * @Description TODO
 * @createTime 2020年08月05日 17:42:00
 */

public interface IPermissionDao extends BaseMapper<PermissionEntity> {

    /**
     * 根据用户Id查询用户权限集合
     * @param userId
     * @return
     */
    public List<String> selectPermsByUserId(Long userId);

    /**
     * 根据状态查询全部资源
     *
     * @param status 状态
     * @return the list
     */
    List<PermissionEntity> selectAllPerms(Integer status);

    /**
     * 分页查询权限
     * @param page
     * @param permsName
     * @param status
     * @param beginTime
     * @param endTime
     * @return
     */
    IPage<PermissionEntity> selectPerms(@Param("page") IPage<PermissionEntity> page, String permsName, Integer status, String beginTime, String endTime);

    /**
     * 根据角色Id查询权限
     * @param roleId
     * @return
     */
    public List<PermissionEntity> selectPermsByRoleId(Long roleId);

    /**
     * 查询子权限条数
     * @param permissionId
     * @return
     */
    public long selectSubPermsByPermissionId(Long permissionId);

    /**
     * 根据权限Id查询权限
     * @param permissionId
     * @return
     */
    public PermissionEntity selectByPermissionId(Long permissionId);
}
