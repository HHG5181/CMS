package com.roots.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.roots.cms.entity.UserEntity;
import com.roots.cms.vo.UserOnlineVo;

import java.io.Serializable;
import java.util.List;

/**
 * @author admin
 * @ClassName IUserService.java
 * @Description 用户服务接口
 * @createTime 2020年08月05日 16:01:00
 */

public interface IUserService extends IService<UserEntity> {

    /**
     * 根据用户名查询用户
     * @param userName
     * @return
     */
    UserEntity selectByUserName(String userName);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    public int updateUser(UserEntity user);

    /**
     * 分页查询用户信息
     * @param userName
     * @param status
     * @param createTime
     * @param pageNum
     * @param pageSize
     * @return
     */
    IPage<UserEntity> selectUsers(String userName, Integer status, String createTime, Integer pageNum, Integer pageSize);

    /**
     * 查询用户列表
     * @return
     */
    List<UserEntity> selectUser();

    /**
     * 根据用户Id查询用户信息
     * @param userId
     * @return
     */
    UserEntity selectByUserId(Long userId);

    /**
     * 通过用户Id删除用户信息
     * @param userId
     */
    public int deleteByUserId(Long userId);

    /**
     * 批量删除用户
     * @param ids
     * @return
     */
    public int batchDelete(String ids) throws Exception;

    /**
     * 根据用户Id分配角色集合
     * @param userId
     * @param roleIds
     */
    public void addAssignRole(Long userId, String roleIds);

    /**
     * 登出
     * @param sessionId
     * @param userName
     */
    public void kickout(Serializable sessionId, String userName);

    /**
     * 在线用户集合
     * @param userOnlineVo
     * @return
     */
    public List<UserOnlineVo> selectOnlines(UserOnlineVo userOnlineVo);

}
