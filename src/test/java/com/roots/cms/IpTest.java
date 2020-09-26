package com.roots.cms;

import com.roots.cms.service.impl.IpServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author admin
 * @ClassName IpTest.java
 * @Description TODO
 * @createTime 2020年08月07日 22:14:00
 */
public class IpTest extends CmsApplicationTests {

    @Autowired
    private IpServiceImpl ipService;

    @Test
    public void testIp() {
        String ipArea = ipService.getIpArea("14.210.79.105");
        System.out.println(ipArea);
    }
}
