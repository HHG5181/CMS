package com.roots.cms;

import com.roots.cms.entity.PermissionEntity;
import com.roots.cms.entity.RoleEntity;
import com.roots.cms.entity.UserEntity;
import com.roots.cms.service.IRoleService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author admin
 * @ClassName RoleTest.java
 * @Description TODO
 * @createTime 2020年08月06日 23:51:00
 */
public class RoleTest extends CmsApplicationTests {

    @Autowired
    private IRoleService roleService;

    @Test
    public void selectRolesByUserId() {
        Set<String> strings = roleService.selectRoleKeyByUserId(5L);
        System.out.println(strings);
    }

    @Test
    public void selectRoles() {
//        RoleEntity entity = new RoleEntity();
//        IPage<RoleEntity> roleEntityIPage = roleService.selectRoles(entity, 1, 4);
//        System.out.println(roleEntityIPage.getRecords());

    }

    @Test
    public void add() {
        RoleEntity entity = new RoleEntity();
        entity.setStatus(0);
        entity.setRoleKey("add");
        entity.setRoleName("普通");
        roleService.insert(entity);
    }

    @Test
    public void delete() {
        List<Long> roleIds = new ArrayList<>();
        roleIds.add(3L);
        roleService.batchDelete(roleIds);
    }

    @Test
    public void selectByRoleId() {
        List<UserEntity> userEntities = roleService.selectByRoleId(2L);
        for (UserEntity userEntity : userEntities) {
            System.out.println(userEntity.getNickName());
        }
    }

    @Test
    public void selectRoleByRoleId() {
        RoleEntity entity = roleService.selectRoleByRoleId(2L);
        System.out.println(entity.getRoleName());
    }

    @Test
    public void selectPerms() {
        List<PermissionEntity> permissionEntityList = roleService.selectPermsByRoleId(2L);
        for (PermissionEntity permissionEntity : permissionEntityList) {
            System.out.println(permissionEntity.getName());
        }
    }

    @Test
    public void addAssign() {

//        roleService.addAssignPerms(4L, "2?0101");

    }

    @Test
    public void selectTree() {
//        List<PermissionEntity> permissionEntities = roleService.selectPermsTree();
//        System.out.println(permissionEntities.toString());
    }

}
