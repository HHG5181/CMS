package com.roots.cms.controller;

import com.roots.cms.service.impl.IpServiceImpl;
import com.roots.cms.utils.ResponseVo;
import com.roots.cms.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author admin
 * @ClassName UtilsController.java
 * @Description TODO
 * @createTime 2020年08月07日 22:26:00
 */
@Controller
@Api(value = "工具接口", description = "工具接口Api")
@RequestMapping("/util")
public class UtilsController {

    @Autowired
    private IpServiceImpl ipService;

    /**
     * 根据IP获取IP信息
     * @param ip
     * @return
     */
    @PostMapping("/ip")
    @ApiOperation(value = "IP归属地", notes = "查询Ip归属地")
    @ApiImplicitParam(name = "ip", value = "输入IP地址", required = true, dataType = "string", paramType = "query")
    @ResponseBody
    public ResponseVo getIpAddress(String ip) {
        String ipArea = ipService.getIpArea(ip);
        return ResultUtils.success("查询成功", ipArea);
    }
}
