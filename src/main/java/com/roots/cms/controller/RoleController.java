package com.roots.cms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.roots.cms.config.shiro.UserRealm;
import com.roots.cms.entity.RoleEntity;
import com.roots.cms.entity.UserEntity;
import com.roots.cms.service.IPermissionService;
import com.roots.cms.service.IRoleService;
import com.roots.cms.utils.*;
import com.roots.cms.vo.Ztree;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author admin
 * @ClassName RoleController.java
 * @Description TODO
 * @createTime 2020年08月05日 22:16:00
 */
@Controller
@RequestMapping("/admin/role")
@Api(value = "角色管理", description = "角色管理Api")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private UserRealm userRealm;

    @Autowired
    private IPermissionService permissionService;

    /**
     * 分页获取角色信息
     * @param roleName
     * @param status
     * @param createTime
     * @param pageNum
     * @param pageSize
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String list(String roleName, Integer status, String createTime, Integer pageNum, Integer pageSize, Model model) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        IPage<RoleEntity> rolePage = roleService.selectRoles(roleName, status, createTime, pageNum, pageSize);
        model.addAttribute("ipage", rolePage);
        return "admin/role/list.html";
    }

    /**
     * 改变角色状态
     * @param roleId
     * @return
     */
    @PostMapping("/change_status")
    @ResponseBody
    public ResponseVo changeStatus(Long roleId) {
        RoleEntity role = roleService.selectRoleByRoleId(roleId);
        if (role.getStatus().equals(Constants.STATUS_VALID)) {
            role.setStatus(Constants.STATUS_INVALID);
        } else {
            role.setStatus(Constants.STATUS_VALID);
        }
        int i = roleService.updateRole(role);
        if (i == 0) {
            return ResultUtils.error("修改失败");
        }
        return ResultUtils.success("修改成功");
    }

    /**
     * 添加角色
     * @return
     */
    @GetMapping("/add")
    public String add() {
        return "/admin/role/form.html";
    }

    /**
     * 添加角色
     * @param roleKey
     * @param roleName
     * @param description
     * @return
     */
    @PostMapping("/add")
    @ResponseBody
    public ResponseVo addRole(String roleKey, String roleName, String description) {
        RoleEntity role = new RoleEntity();
        role.setRoleKey(roleKey);
        role.setDescription(description);
        role.setRoleName(roleName);
        role.setCreateTime(DateUtils.getNowDate());
        role.setStatus(Constants.STATUS_VALID);
        role.setUpdateTime(DateUtils.getNowDate());
        try {
            int rows = roleService.insert(role);
            if (rows > 0) {
                return ResultUtils.success("添加角色成功");
            } else {
                return ResultUtils.error("添加角色失败");
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     *
     * @param roleId
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    public ResponseVo deleteRole(Long roleId) {
        if (roleService.selectByRoleId(roleId).size() > 0) {
            return ResultUtils.error("删除失败，该角色下存在用户");
        }
        List<Long> list = Collections.singletonList(roleId);
        int rows = roleService.batchDelete(list);
        if (rows > 0) {
            return ResultUtils.success("删除角色成功");
        } else {
            return ResultUtils.error("删除失败");
        }
    }

    /**
     * 批量删除角色
     * @param ids
     * @return
     */
    @PostMapping("/batch/delete")
    @ResponseBody
    public ResponseVo batchDelete(String[] ids) {
        List<String> roleIds = Arrays.asList(ids);
        List<Long> rIds = new ArrayList<>();
        for (String roleId : roleIds) {
            rIds.add(Long.parseLong(roleId));
        }
        if (CollectionUtils.isNotEmpty(roleService.selectByRoleIds(rIds))) {
            return ResultUtils.error("删除失败，选中角色下存在用户");
        }
        int rows = roleService.batchDelete(rIds);
        if (rows > 0) {
            return ResultUtils.success("删除角色成功");
        } else {
            return ResultUtils.error("删除角色失败");
        }
    }

    /**
     * 编辑角色详细
     * @param model
     * @param roleId
     * @return
     */
    @GetMapping("/edit")
    public String edit(Model model, Long roleId) {
        RoleEntity role = roleService.selectRoleByRoleId(roleId);
        model.addAttribute("role", role);
        return "/admin/role/edit.html";
    }

    /**
     * 编辑角色
     * @param roleId
     * @param roleKey
     * @param roleName
     * @param description
     * @return
     */
    @PostMapping("/edit/{roleId}")
    @ResponseBody
    public ResponseVo editRole(@PathVariable("roleId") Long roleId, String roleKey, String roleName, String description) {
        RoleEntity role = new RoleEntity();
        role.setDescription(description);
        role.setUpdateTime(DateUtils.getNowDate());
        role.setRoleName(roleName);
        role.setRoleId(roleId);
        role.setRoleKey(roleKey);
        boolean b = roleService.updateById(role);
        if (b) {
            return ResultUtils.success("编辑成功");
        } else {
            return ResultUtils.error("编辑角色失败");
        }
    }

    /**
     * 访问授权
     * @return
     */
    @GetMapping("/apply")
    public String apply(Model model, Long roleId) {
        model.addAttribute("roleId", roleId);
        return "/admin/role/apply.html";
    }

    /**
     * 权限列表查询
     * @param roleId
     * @return
     */
    @PostMapping("/perms")
    @ResponseBody
    public ResponseVo assignRole(Long roleId) {
        List<Ztree> ztrees = roleService.selectPermsTree(roleId);
        return ResultUtils.success("获取列表成功", ztrees);
    }

    /**
     * 分配权限
     * @param roleId
     * @param pids
     * @return
     */
    @PostMapping("/assign")
    @ResponseBody
    public ResponseVo assignRole(Long roleId, @RequestParam("pids[]") Long[] pids) {
        try {
            roleService.addAssignPerms(roleId, pids);
            List<UserEntity> users = roleService.selectByRoleId(roleId);
            if (!users.isEmpty()) {
                List<Long> userIds = new ArrayList<>();
                for (UserEntity user : users) {
                    userIds.add(user.getUserId());
                }
                userRealm.clearAuthorizationByUserId(userIds);
            }
            return ResultUtils.success("分配权限成功");
        } catch (Exception e) {
            return ResultUtils.error("分配权限失败");
        }
    }
}
