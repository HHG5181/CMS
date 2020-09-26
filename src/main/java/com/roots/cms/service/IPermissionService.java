package com.roots.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.roots.cms.entity.PermissionEntity;

import java.util.List;
import java.util.Set;

/**
 * @author admin
 * @Description 权限服务接口
 * @createTime 2020年08月05日 17:42:00
 */

public interface IPermissionService extends IService<PermissionEntity> {

    /**
     * 根据用户Id查询用户权限集合
     * @param userId
     * @return
     */
    public Set<String> selectPermsByUserId(Long userId);

    /**
     * 查询所有权限
     * @param status
     * @return
     */
    public List<PermissionEntity> selectAll(Integer status);

    /**
     * 分页查询权限
     * @param permsName
     * @param status
     * @param createTime
     * @param pageNum
     * @param pageSize
     * @return
     */
    IPage<PermissionEntity> selectPerms(String permsName, Integer status, String createTime, Integer pageNum, Integer pageSize);


    /**
     * 添加权限
     * @param permission
     * @return
     */
    public int insert(PermissionEntity permission);

    /**
     * 查询子权限条数
     * @param permissionId
     * @return
     */
    public long selectSubPermsByPermissionId(Long permissionId);

    /**
     * 根据权限Id删除权限
     * @param permissionId
     * @return
     */
    public int deletePerm(Long permissionId);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    public int batchDelete(Long[] ids);

    /**
     * 根据权限Id查询权限
     * @param permissionId
     * @return
     */
    public PermissionEntity selectByPermissionId(Long permissionId);
}
