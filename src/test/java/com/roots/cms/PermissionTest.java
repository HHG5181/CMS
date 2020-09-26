package com.roots.cms;

import com.roots.cms.entity.PermissionEntity;
import com.roots.cms.service.IPermissionService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

/**
 * @author admin
 * @ClassName PermissionTest.java
 * @Description TODO
 * @createTime 2020年08月07日 11:17:00
 */
public class PermissionTest extends CmsApplicationTests {

    @Autowired
    private IPermissionService permissionService;

    @Test
    public void selectPermsByUserId() {
        Set<String> strings = permissionService.selectPermsByUserId(11L);
        for (String id : strings) {
            System.out.println(id);
        }
    }

    @Test
    public void selectAll() {
        List<PermissionEntity> permissionEntityList = permissionService.selectAll(0);
        for (PermissionEntity perm : permissionEntityList) {
            System.out.println(perm.getName());
        }
    }

    @Test
    public void add() {
        PermissionEntity entity = new PermissionEntity();
        entity.setStatus(0);
        entity.setPerms("1241312");
        entity.setType(421456);
        entity.setUrl("4512354asdf");
        entity.setName("测试");
        permissionService.insert(entity);
    }

    @Test
    public void delete() {
        int i = permissionService.deletePerm(3L);
        System.out.println(i);
    }

    @Test
    public void selct() {
        PermissionEntity permissionEntity = permissionService.selectByPermissionId(3L);
        System.out.println(permissionEntity.getName());
    }
}
