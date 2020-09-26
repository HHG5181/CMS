package com.roots.cms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.roots.cms.config.shiro.ShiroService;
import com.roots.cms.entity.PermissionEntity;
import com.roots.cms.service.IPermissionService;
import com.roots.cms.utils.Constants;
import com.roots.cms.utils.DateUtils;
import com.roots.cms.utils.ResponseVo;
import com.roots.cms.utils.ResultUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author admin
 * @ClassName PermissionController.java
 * @Description TODO
 * @createTime 2020年08月05日 22:16:00
 */
@Controller
@RequestMapping("/admin/perms")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private ShiroService shiroService;

    /**
     * 权限列表
     * @param permsName
     * @param status
     * @param createTime
     * @param pageNum
     * @param pageSize
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String list(String permsName, Integer status, String createTime, Integer pageNum, Integer pageSize, Model model) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        IPage<PermissionEntity> permsPage = permissionService.selectPerms(permsName, status, createTime, pageNum, pageSize);
        model.addAttribute("ipage", permsPage);
        return "admin/perms/list.html";
    }

    /**
     * 更改状态
     * @param permsId
     * @return
     */
    @PostMapping("/change_status")
    @ResponseBody
    public ResponseVo changeStatus(Long permsId) {
        PermissionEntity permissionEntity = permissionService.selectByPermissionId(permsId);
        if (permissionEntity.getStatus().equals(Constants.STATUS_VALID)) {
            permissionEntity.setStatus(Constants.STATUS_INVALID);
        } else {
            permissionEntity.setStatus(Constants.STATUS_VALID);
        }
        boolean b = permissionService.updateById(permissionEntity);
        if (b) {
            return ResultUtils.success("修改成功");
        }
        return ResultUtils.error("修改失败");
    }

    /**
     * 添加权限
     * @return
     */
    @GetMapping("/add")
    public String add(Long pid, Model model) {
        model.addAttribute("pid", pid);
        return "admin/perms/form.html";
    }

    /**
     * 添加权限
     * @param name
     * @param perms
     * @param url
     * @param description
     * @return
     */
    @PostMapping("/add")
    @ResponseBody
    public ResponseVo addPerm(Long pid, String name, String perms, String url, String description) {
        PermissionEntity permission = new PermissionEntity();
        permission.setCreateTime(DateUtils.getNowDate());
        permission.setDescription(description);
        permission.setName(name);
        permission.setPerms(perms);
        permission.setUrl(url);
        permission.setParentId(pid);
        try {
            int a = permissionService.insert(permission);
            if (a > 0) {
                shiroService.updatePermission();
                return ResultUtils.success("添加权限成功");
            } else {
                return ResultUtils.error("添加权限失败");
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 删除权限
     * @param permsId
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    public ResponseVo deletePerm(Long permsId) {
        try {
            long count = permissionService.selectSubPermsByPermissionId(permsId);
            if (count > 0) {
                return ResultUtils.error("该权限存在子权限，无法删除");
            }
            int rows = permissionService.deletePerm(permsId);
            if (rows > 0) {
                shiroService.updatePermission();
                return ResultUtils.success("删除权限成功");
            } else {
                return ResultUtils.error("删除权限失败");
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PostMapping("/batch/delete")
    @ResponseBody
    public ResponseVo batchDelete(Long[] ids) {
        try {
            for (Long id : ids) {
                long count = permissionService.selectSubPermsByPermissionId(id);
                if (count > 0) {
                    return ResultUtils.error("权限存在子权限，无法删除");
                }
            }
            int rows = permissionService.batchDelete(ids);
            if (rows > 0) {
                shiroService.updatePermission();
                return ResultUtils.success("删除权限成功");
            } else {
                return ResultUtils.error("删除权限失败");
            }
        } catch (Exception e) {
            throw e;
        }
    }


    /**
     * 编辑权限
     * @param model
     * @param permsId
     * @return
     */
    @GetMapping("/edit")
    public String editPerm(Model model, Long permsId) {
        PermissionEntity permission = permissionService.selectByPermissionId(permsId);
        model.addAttribute("perms", permission);
        return "/admin/perms/edit.html";
    }

    /**
     * 编辑权限
     * @param permission
     * @return
     */
    @PostMapping("/edit")
    @ResponseBody
    @ApiOperation(value = "编辑权限", notes = "编辑权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "permissionId", value = "权限Id", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "权限名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "perms", value = "权限标识", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "url", value = "权限路径", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "description", value = "描述", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "icon", value = "图标", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "orderNum", value = "排序", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "parentId", value = "父Id", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "权限类型", dataType = "int", paramType = "query"),
    })
    public ResponseVo edit(Long permsId, String name, String perms, String url, String description) {
        PermissionEntity permission = new PermissionEntity();
        permission.setPermissionId(permsId);
        permission.setName(name);
        permission.setPerms(perms);
        permission.setUrl(url);
        permission.setDescription(description);
        boolean b = permissionService.updateById(permission);
        if (b) {
            shiroService.updatePermission();
            return ResultUtils.success("编辑权限成功");
        } else {
            return ResultUtils.error("编辑权限失败");
        }
    }
}
