package com.roots.cms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.roots.cms.entity.UserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author admin
 * @Description 用户数据层Dao
 * @createTime 2020年08月05日 15:44:00
 */

public interface IUserDao extends BaseMapper<UserEntity> {

    /**
     * 通过用户名查询用户
     * @param userName
     * @return
     */
    public UserEntity selectUserByUserName(String userName);

    /**
     * 根据user参数查询用户列表
     * @param page
     * @param userName
     * @param status
     * @param beginTime
     * @param endTime
     * @return
     */
    IPage<UserEntity> selectUsers(@Param("page") IPage<UserEntity> page, String userName, Integer status, String beginTime, String endTime);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    public int updateUser(UserEntity user);

    /**
     * 根据角色Id下的所有用户
     * @param roleId
     * @return
     */
    List<UserEntity> selectByRoleId(Long roleId);
}
