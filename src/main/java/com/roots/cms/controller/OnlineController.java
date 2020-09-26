package com.roots.cms.controller;

import com.roots.cms.service.IUserService;
import com.roots.cms.utils.PageResultVo;
import com.roots.cms.utils.ResultUtils;
import com.roots.cms.vo.UserOnlineVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @author admin
 * @ClassName OnlineController.java
 * @Description TODO
 * @createTime 2020年08月08日 15:32:00
 */
@Controller
@RequestMapping("/online/user")
@Api(value = "在线用户管理", description = "在线用户管理Api")
public class OnlineController {

    @Autowired
    private IUserService userService;

    /**
     * 在线用户列表查询
     * @param user
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ResponseBody
    @PostMapping
    @ApiOperation(value = "在线用户列表", notes = "在线用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "页数", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "条数", dataType = "Integer", paramType = "query"),
    })
    public PageResultVo onlineUsers(@ApiIgnore UserOnlineVo user, Integer pageNum, Integer pageSize) {
        List<UserOnlineVo> onlineVos = userService.selectOnlines(user);
        int end = Math.min(pageNum * pageSize, onlineVos.size());
        return ResultUtils.table(onlineVos.subList((pageNum - 1) * pageSize, end), (long) onlineVos.size());
    }
}
