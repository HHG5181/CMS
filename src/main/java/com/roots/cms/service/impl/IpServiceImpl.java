package com.roots.cms.service.impl;

import com.roots.cms.utils.IpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author admin
 * @ClassName IpServiceImpl.java
 * @Description TODO
 * @createTime 2020年08月07日 22:04:00
 */
@Service
public class IpServiceImpl {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static IpUtils ipUtils;

    @Value("${qqwry.dat.path}")
    private String filepath;

    /**
     * ip地址信息
     * @param ip
     * @return
     */
    public String getIpArea(String ip)  {
        if (ipUtils == null) {
            try {
                ipUtils = new IpUtils(new File(filepath));
            } catch (Exception e) {
                logger.error("IP地址库实例化出错");
            }
        }
        return ipUtils.getAddress(ip);
    }

}
