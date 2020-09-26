package com.roots.cms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.roots.cms.entity.RoleEntity;
import com.roots.cms.entity.UserEntity;
import com.roots.cms.service.IRoleService;
import com.roots.cms.service.IUserService;
import com.roots.cms.utils.*;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author admin
 * @ClassName UserController.java
 * @Description TODO
 * @createTime 2020年08月05日 20:16:00
 */
@Controller
@RequestMapping("/admin/user")
@Api(value = "用户管理", description = "用户管理Api")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    /**
     * 分页获取用户列表
     * @return
     */
    @GetMapping("/list")
    public String userList(String userName, Integer status, String createTime, Integer pageNum, Integer pageSize, Model model) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        IPage<UserEntity> userEntityIPage = userService.selectUsers(userName, status, createTime, pageNum, pageSize);
        model.addAttribute("ipage", userEntityIPage);
        return "admin/user/list.html";
    }

    /**
     * 新增用户
     * @return
     */
    @GetMapping("/add")
    public String add(Model model) {
        List<RoleEntity> list = roleService.selectRoleEntities();
        model.addAttribute("roles", list);
        return "admin/user/form.html";
    }

    /**
     * 添加用户信息
     * @param username
     * @param nickname
     * @param img
     * @param remark
     * @param roleIds
     * @return
     */
    @PostMapping("/add")
    @ResponseBody
    public ResponseVo add(String username, String nickname, String img, String remark, String roleIds) {
        UserEntity userEntity = userService.selectByUserName(username);
        if (userEntity != null) {
            return ResultUtils.error("用户已存在");
        }
        UserEntity user = new UserEntity();
        user.setPassword(Constants.PASSWORD);
        user.setNickName(nickname);
        user.setImg(img);
        user.setUserName(username);
        user.setRemark(remark);
        user.setStatus(Constants.STATUS_VALID);
        user.setCreateTime(DateUtils.getNowDate());
        user.setUpdateTime(DateUtils.getNowDate());
        PasswordUtils.encryptPassword(user);
        boolean flag = userService.save(user);
        if (flag) {
            UserEntity userId = userService.selectByUserName(username);
            userService.addAssignRole(userId.getUserId(), roleIds);
            return ResultUtils.success("添加成功");
        } else {
            return ResultUtils.error("添加失败");
        }
    }

    /**
     * 改变用户状态
     * @param userId
     * @return
     */
    @PostMapping("/change_status")
    @ResponseBody
    public ResponseVo changeStatus(Long userId) {
        UserEntity user = userService.selectByUserId(userId);
        if (user.getStatus().equals(Constants.STATUS_VALID)) {
            user.setStatus(Constants.STATUS_INVALID);
        } else {
            user.setStatus(Constants.STATUS_VALID);
        }
        int i = userService.updateUser(user);
        if (i == 0) {
            return ResultUtils.error("修改失败");
        }
        return ResultUtils.success("修改成功");
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    public ResponseVo deleteUser(Long userId) {
        int i = userService.deleteByUserId(userId);
        if (i > 0) {
            return ResultUtils.success("删除用户成功");
        } else {
            return ResultUtils.error("删除用户失败");
        }
    }

    /**
     * 重置用户密码
     * @param userId
     * @return
     */
    @GetMapping("reset")
    @ResponseBody
    public ResponseVo reset(Long userId) {
        UserEntity userEntity = userService.selectByUserId(userId);
        userEntity.setPassword(Constants.PASSWORD);
        PasswordUtils.encryptPassword(userEntity);
        int row = userService.updateUser(userEntity);
        if (row > 0) {
            return ResultUtils.success("重置成功");
        } else {
            return ResultUtils.error("重置失败");
        }
    }

    /**
     * 批量删除用户
     * @param ids
     * @return
     */
    @PostMapping("/batch/delete")
    @ResponseBody
    public ResponseVo batchDeleteUser(@RequestParam("ids") String ids) throws Exception {
        System.out.println(ids);
        int rows = userService.batchDelete(ids);
        if (rows > 0) {
            return ResultUtils.success("删除用户成功");
        } else {
            return ResultUtils.error("删除用户失败");
        }
    }

    /**
     * 编辑用户
     * @param model
     * @param userId
     * @return
     */
    @GetMapping("/edit")
    public String userInfo(Model model,Long userId) {
        UserEntity userEntity = userService.selectByUserId(userId);
        model.addAttribute("user", userEntity);
        List<RoleEntity> list = roleService.list(Wrappers.<RoleEntity>lambdaQuery().eq(RoleEntity::getStatus, Constants.STATUS_VALID));
        List<String> hasRoles = roleService.selectRolesByUserId(userId);
        model.addAttribute("roles", list);
        model.addAttribute("hasRoles", hasRoles);
        return "admin/user/edit.html";
    }

    /**
     * 保存编辑信息
     * @param userId
     * @param username
     * @param nickname
     * @param img
     * @param remark
     * @param roleIds
     * @return
     */
    @PostMapping("/edit/{userId}")
    @ResponseBody
    public ResponseVo editUser(@PathVariable("userId") Long userId, String username, String nickname, String img, String remark, String roleIds) {
        UserEntity user = new UserEntity();
        user.setNickName(nickname);
        user.setImg(img);
        user.setUserName(username);
        user.setRemark(remark);
        user.setUpdateTime(DateUtils.getNowDate());
        user.setUserId(userId);
        int i = userService.updateUser(user);
        if (i > 0) {
            userService.addAssignRole(userId, roleIds);
            return ResultUtils.success("修改成功");
        } else {
            return ResultUtils.error("修改失败");
        }
    }

}
