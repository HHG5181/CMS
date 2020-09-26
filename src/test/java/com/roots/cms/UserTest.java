package com.roots.cms;

import com.roots.cms.common.exception.BusinessException;
import com.roots.cms.dao.IUserDao;
import com.roots.cms.entity.UserEntity;
import com.roots.cms.service.IUserService;
import com.roots.cms.utils.DateUtils;
import com.roots.cms.utils.PasswordUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author admin
 * @ClassName UserTest.java
 * @Description TODO
 * @createTime 2020年08月05日 16:11:00
 */

public class UserTest extends CmsApplicationTests {

    @Autowired
    private IUserService userService;

    @Test
    public void selectByUserName() {
        String userName = "admin";

        UserEntity userEntity = userService.selectByUserName(userName);

        System.out.println(userEntity.getNickName());
    }

    @Test
    public void add() {
        UserEntity user = new UserEntity();
        String username = "admin";
        String password = "admin";
        user.setCreateTime(DateUtils.getNowDate());
        user.setStatus(0);
        user.setNickName("小鸟");
        user.setPassword(password);
        user.setUserName(username);
        user.setUpdateTime(DateUtils.getNowDate());
        PasswordUtils.encryptPassword(user);
        userService.save(user);
    }

    @Test
    public void updateUser() {
        UserEntity user = new UserEntity();
        user.setCreateTime(DateUtils.getNowDate());
        user.setUserId(3L);
        user.setNickName("大鸟");
        userService.updateUser(user);
    }

    @Autowired
    private IUserDao userDao;

    @Test
    public void selectUser() {
//        UserEntity userEntity = new UserEntity();
//        IPage<UserEntity> userEntityIPage = userService.selectUsers(userEntity, 1, 3);
//        System.out.println(userEntityIPage.getRecords());
    }


    @Test
    public void selectUsers() {
        List<UserEntity> userEntities = userService.selectUser();
        for(UserEntity user : userEntities) {
            System.out.println(user.getNickName());
        }
    }

    @Test
    public void selectByUserId() {

        Long id = 3L;
        UserEntity userEntity = userService.selectByUserId(id);
        System.out.println(userEntity.getNickName());
    }

    @Test
    public void deleteByUserId() {
        int i = userService.deleteByUserId(3L);
    }

    @Test
    public void deleteBatch() {
        String ids = ("4");
        try {
            userService.batchDelete(ids);
        } catch (Exception e) {
            throw new BusinessException("删除失败");
        }
    }

    @Test
    public void addAssignRole() {
        Long userId = 5L;

        userService.addAssignRole(userId, "2");
    }
}
