package com.roots.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.roots.cms.entity.PermissionEntity;
import com.roots.cms.entity.RoleEntity;
import com.roots.cms.entity.UserEntity;
import com.roots.cms.vo.Ztree;

import java.util.List;
import java.util.Set;

/**
 * @author admin
 * @Description TODO
 * @createTime 2020年08月05日 17:32:00
 */

public interface IRoleService extends IService<RoleEntity> {

    /**
     * 根据用户Id查询角色集合
     * @param userId
     * @return
     */
    public Set<String> selectRoleKeyByUserId(Long userId);

    /**
     * 根据条件查询角色列表
     * @param roleName
     * @param status
     * @param createTime
     * @param pageNum
     * @param pageSize
     * @return
     */
    IPage<RoleEntity> selectRoles(String roleName, Integer status, String createTime, Integer pageNum, Integer pageSize);

    /**
     * 查询角色集合
     * @return
     */
    List<RoleEntity> selectRoleEntities();

    /**
     * 根据用户Id查询角色集合
     * @param userId
     * @return
     */
    List<String> selectRolesByUserId(Long userId);

    /**
     * 添加角色
     * @param role
     * @return
     */
    public int insert(RoleEntity role);

    /**
     * 修改角色信息
     * @param roleEntity
     * @return
     */
    public int updateRole(RoleEntity roleEntity);

    /**
     * 根据角色Id下的所有用户
     * @param roleId
     * @return
     */
    List<UserEntity> selectByRoleId(Long roleId);

    /**
     * 批量删除用户
     * @param roleIds
     * @return
     */
    public int batchDelete(List<Long> roleIds);

    /**
     * 角色Id下的所有用户
     * @param roleIds
     * @return
     */
    public List<UserEntity> selectByRoleIds(List<Long> roleIds);

    /**
     * 根据Id查询角色
     * @param roleId
     * @return
     */
    public RoleEntity selectRoleByRoleId(Long roleId);

    /**
     * 查询权限树
     * @return
     */
    public List<Ztree> selectPermsTree(Long roleId);

    /**
     * 根据角色Id查询权限集合
     * @param roleId
     * @return
     */
    public List<PermissionEntity> selectPermsByRoleId(Long roleId);

//    /**
//     * 权限树结构
//     * @return
//     */
//    public List<Ztree> permsTreeData();
//
//    /**
//     * 对象转权限树
//     * @param perms
//     * @return
//     */
//    public List<Ztree> initZtree(List<PermissionEntity> perms);
//
//    /**
//     * 对象转权限树
//     * @param perms
//     * @param rolePerms
//     * @param permsFlag
//     * @return
//     */
//    public List<Ztree> initZtree(List<PermissionEntity> perms, List<String> rolePerms, boolean permsFlag);

    /**
     * 根据用户Id分配角色
     * @param roleId
     * @param permsIds
     */
    public void addAssignPerms(Long roleId, Long[] permsIds);

    /**
     * 通过角色ID查询角色使用数量
     * @param roleId 角色ID
     * @return 结果
     */
    public int countUserRoleByRoleId(Long roleId);
}
