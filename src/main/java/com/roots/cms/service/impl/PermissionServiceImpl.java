package com.roots.cms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roots.cms.dao.IPermissionDao;
import com.roots.cms.entity.PermissionEntity;
import com.roots.cms.service.IPermissionService;
import com.roots.cms.utils.Constants;
import com.roots.cms.utils.DateUtils;
import com.roots.cms.utils.Pagination;
import com.roots.cms.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author admin
 * @ClassName PermissionServiceImpl.java
 * @Description 权限服务实现类
 * @createTime 2020年08月05日 17:43:00
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<IPermissionDao, PermissionEntity> implements IPermissionService {

    @Autowired
    private IPermissionDao permisionDao;

    @Override
    public Set<String> selectPermsByUserId(Long userId) {

        List<String> perms = permisionDao.selectPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public List<PermissionEntity> selectAll(Integer status) {
        return permisionDao.selectAllPerms(status);
    }

    @Override
    public IPage<PermissionEntity> selectPerms(String permsName, Integer status, String createTime, Integer pageNum, Integer pageSize) {
        IPage<PermissionEntity> page = new Pagination<>(pageNum, pageSize);
        String beginTime = "";
        String endTime = "";
        if (createTime != null) {
            beginTime = StringUtils.split("/")[0];
            endTime = StringUtils.split("/")[1];
        }
        return permisionDao.selectPerms(page, permsName, status, beginTime, endTime);
    }

    @Override
    public int insert(PermissionEntity permission) {
        permission.setStatus(Constants.STATUS_VALID);
        permission.setCreateTime(DateUtils.getNowDate());
        permission.setUpdateTime(DateUtils.getNowDate());
        return permisionDao.insert(permission);
    }

    @Override
    public long selectSubPermsByPermissionId(Long permissionId) {
        return permisionDao.selectSubPermsByPermissionId(permissionId);
    }

    @Override
    public int deletePerm(Long permissionId) {
        return permisionDao.deleteById(permissionId);
    }

    @Override
    public int batchDelete(Long[] ids) {
        List<Long> list = Arrays.asList(ids);
        return permisionDao.deleteBatchIds(list);
    }

    @Override
    public PermissionEntity selectByPermissionId(Long permissionId) {
        return permisionDao.selectByPermissionId(permissionId);
    }
}
