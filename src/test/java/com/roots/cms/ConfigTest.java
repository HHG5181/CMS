package com.roots.cms;

import com.roots.cms.dao.IConfigDao;
import com.roots.cms.service.IConfigService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author admin
 * @ClassName ConfigTest.java
 * @Description TODO
 * @createTime 2020年08月16日 17:06:00
 */
public class ConfigTest extends CmsApplicationTests {

    @Autowired
    private IConfigService configService;

    @Autowired
    private IConfigDao configDao;

    @Test
    public void select() {
        Map<String, String> map = configService.selectAll();
        System.out.println(map);
    }
}
