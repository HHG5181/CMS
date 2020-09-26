package com.roots.cms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roots.cms.common.exception.BusinessException;
import com.roots.cms.dao.*;
import com.roots.cms.entity.PermissionEntity;
import com.roots.cms.entity.RoleEntity;
import com.roots.cms.entity.RolePermission;
import com.roots.cms.entity.UserEntity;
import com.roots.cms.service.IRoleService;
import com.roots.cms.utils.*;
import com.roots.cms.vo.Ztree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author admin
 * @ClassName RoleServiceImpl.java
 * @Description TODO
 * @createTime 2020年08月05日 17:33:00
 */

@Service
public class RoleServiceImpl extends ServiceImpl<IRoleDao, RoleEntity> implements IRoleService {

    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IUserRoleDao userRoleDao;

    @Autowired
    private IRolePermissionDao rolePermissionDao;

    @Autowired
    private IPermissionDao permissionDao;

    @Override
    public Set<String> selectRoleKeyByUserId(Long userId) {
        return roleDao.selectRoleKeyByUserId(userId);
    }

    @Override
    public IPage<RoleEntity> selectRoles(String roleName, Integer status, String createTime, Integer pageNum, Integer pageSize) {
        IPage<RoleEntity> page = new Pagination<>(pageNum, pageSize);
        String beginTime = "";
        String endTime = "";
        if (createTime != null) {
            beginTime = StringUtils.split("/")[0];
            endTime = StringUtils.split("/")[1];
        }
        return roleDao.selectRoles(page, roleName, status, beginTime, endTime);
    }

    @Override
    public List<RoleEntity> selectRoleEntities() {
        return roleDao.selectRoleEntities();
    }

    @Override
    public List<String> selectRolesByUserId(Long userId) {
        return roleDao.selectRolesByUserId(userId);
    }

    @Override
    public int insert(RoleEntity role) {
        role.setStatus(Constants.STATUS_VALID);
        role.setCreateTime(DateUtils.getNowDate());
        return roleDao.insert(role);
    }

    @Override
    public int updateRole(RoleEntity roleEntity) {
        return roleDao.updateById(roleEntity);
    }

    @Override
    public List<UserEntity> selectByRoleId(Long roleId) {
        return userDao.selectByRoleId(roleId);
    }

    @Override
    public int batchDelete(List<Long> roleIds) {
        for (Long roleId : roleIds) {
            if (countUserRoleByRoleId(roleId) > 0) {
                throw new BusinessException("角色已分配，不能删除");
            }
            //删除角色与权限关联
            rolePermissionDao.delete(Wrappers.<RolePermission>lambdaQuery().eq(RolePermission::getRoleId, roleId));
        }
        return roleDao.deleteBatchIds(roleIds);
    }

    @Override
    public List<UserEntity> selectByRoleIds(List<Long> roleIds) {
        return null;
    }

    @Override
    public RoleEntity selectRoleByRoleId(Long roleId) {
        return roleDao.selectOne(Wrappers.<RoleEntity>lambdaQuery().eq(RoleEntity::getRoleId, roleId));
    }

    @Override
    public List<Ztree> selectPermsTree(Long roleId) {
        List<PermissionEntity> permissionEntities = permissionDao.selectAllPerms(Constants.STATUS_VALID);
        List<Long> perms = roleDao.selectPerms(roleId);
        List<Ztree> ztrees = new ArrayList<>();
        boolean isChecked = StringUtils.isNotNull(perms);
        for (PermissionEntity perm : permissionEntities) {
            Ztree ztree = new Ztree();
            ztree.setId(perm.getPermissionId());
            ztree.setPId(perm.getParentId());
            ztree.setTitle(perm.getName());
            if (isChecked) {
                ztree.setChecked(perms.contains(perm.getPermissionId()));
            }
            ztrees.add(ztree);
        }
        List<Ztree> list = new ArrayList<>();

        for (int i = 0; i < ztrees.size(); i++) {
            //一级菜单没有父菜单
            if (ztrees.get(i).getPId() == null) {
                list.add(ztrees.get(i));
            }
        }

        for (Ztree ztree : list) {
            ztree.set_sub_(getChild(ztree.getId(), ztrees));
        }
        return list;
    }

    /**
     * 递归查找子菜单
     * @param id
     * @param ztrees
     * @return
     */
    private List<Ztree> getChild(Long id, List<Ztree> ztrees) {
        //子菜单
        List<Ztree> childList = new ArrayList<>();
        for (Ztree ztree : ztrees) {
            if (ztree.getPId() != null) {
                if (ztree.getPId().equals(id)) {
                    childList.add(ztree);
                }
            }
        }

        // 把子菜单的子菜单再循环一遍
        for (Ztree ztree : childList) {
            ztree.set_sub_(getChild(ztree.getId(), ztrees));
        }
        //递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }

    @Override
    public List<PermissionEntity> selectPermsByRoleId(Long roleId) {
        return permissionDao.selectPermsByRoleId(roleId);
    }

    public String transPermsName(PermissionEntity perm, boolean permsFlag) {
        StringBuffer sb = new StringBuffer();
        sb.append(perm.getName());
        if (permsFlag) {
            sb.append("<font color=\"#888\">&nbsp;&nbsp;&nbsp;" + perm.getPerms() + "</font>");
        }
        return sb.toString();
    }

    @Override
    public void addAssignPerms(Long roleId, Long[] permsIds) {
        rolePermissionDao.delete(Wrappers.<RolePermission>lambdaQuery().eq(RolePermission::getRoleId, roleId));
        for (Long permsId : permsIds) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permsId);
            rolePermissionDao.insert(rolePermission);
        }
    }

    @Override
    public int countUserRoleByRoleId(Long roleId) {
        return userRoleDao.countUserRoleByRoleId(roleId);
    }
}
