package com.roots.cms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roots.cms.common.exception.BusinessException;
import com.roots.cms.dao.IUserDao;
import com.roots.cms.dao.IUserRoleDao;
import com.roots.cms.entity.UserEntity;
import com.roots.cms.entity.UserRole;
import com.roots.cms.service.IUserService;
import com.roots.cms.utils.Constants;
import com.roots.cms.utils.Convert;
import com.roots.cms.utils.Pagination;
import com.roots.cms.vo.UserOnlineVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

/**
 * @author admin
 * @ClassName UserServiceImpl.java
 * @Description TODO
 * @createTime 2020年08月05日 16:03:00
 */
@Service
public class UserServiceImpl extends ServiceImpl<IUserDao, UserEntity> implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IUserRoleDao userRoleDao;

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private RedisCacheManager redisCacheManager;

    @Autowired
    private RedisSessionDAO redisSessionDAO;

    @Override
    public UserEntity selectByUserName(String userName) {
        return userDao.selectUserByUserName(userName);
    }

    @Override
    public int updateUser(UserEntity user) {
        return userDao.updateById(user);
    }

    @Override
    public IPage<UserEntity> selectUsers(String userName, Integer status, String createTime, Integer pageNum, Integer pageSize) {
        IPage<UserEntity> page = new Pagination<>(pageNum, pageSize);
        String beginTime = "";
        String endTime = "";
        if (createTime != null) {
            beginTime = StringUtils.split("/")[0];
            endTime = StringUtils.split("/")[1];
        }
        return userDao.selectUsers(page, userName, status, beginTime, endTime);
    }

    @Override
    public List<UserEntity> selectUser() {
        return userDao.selectList(Wrappers.<UserEntity>lambdaQuery());
    }

    @Override
    public UserEntity selectByUserId(Long userId) {
        return userDao.selectById(userId);
    }

    @Override
    public int deleteByUserId(Long userId) {
        //删除用户与角色关联
        userRoleDao.delete(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, userId));
        return userDao.deleteById(userId);
    }

    @Override
    public int batchDelete(String ids) throws BusinessException {
        Long[] userIds = Convert.toLongArray(ids);
        List<Long> list = Arrays.asList(userIds);
        return userDao.deleteBatchIds(list);
    }

    @Override
    public void addAssignRole(Long userId, String roleIds) {
        Long[] ids = Convert.toLongArray(roleIds);
        userRoleDao.delete(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, userId));
        for (Long roleId : ids) {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRoleDao.insert(userRole);
        }
    }

    @Override
    public void kickout(Serializable sessionId, String userName) {
        getSessionById(sessionId).setAttribute("kickout", true);
        //读取缓存，找到并从队列中溢出
        Cache<String, Deque<Serializable>> cache = redisCacheManager.getCache(Constants.SHIRO_REDIS_CACHE_NAME);
        Deque<Serializable> deques = cache.get(userName);
        for (Serializable deque : deques) {
            if (sessionId.equals(deque)) {
                deques.remove(deque);
                break;
            }
        }
        cache.put(userName, deques);
    }

    @Override
    public List<UserOnlineVo> selectOnlines(UserOnlineVo userOnlineVo) {
        //redis实现了shiro的session的Dao,直接获取Session列表
        Collection<Session> activeSessions = redisSessionDAO.getActiveSessions();
        Iterator<Session> iterator = activeSessions.iterator();
        List<UserOnlineVo> onlineVos = new ArrayList<>();
        while (iterator.hasNext()) {
            //直接获取session
            Session session = iterator.next();
            //排除已被提出session
            if (session.getAttribute("kickout") != null) {
                continue;
            }
            UserOnlineVo onlineVo = getSessionVo(session);
            if (onlineVo != null) {
                //根据用户名搜索
                if (StringUtils.isNotBlank(userOnlineVo.getUsername())) {
                    if (onlineVo.getUsername().contains(userOnlineVo.getUsername())) {
                        onlineVos.add(onlineVo);
                    }
                } else {
                    onlineVos.add(onlineVo);
                }
            }
        }
        return onlineVos;
    }

    private Session getSessionById(Serializable sessionId) {
        return sessionManager.getSession(new DefaultSessionKey(sessionId));
    }

    private static UserOnlineVo getSessionVo(Session session) {
        //获取session登录信息
        Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        if (obj == null) {
            return null;
        }
        //判断确定SimplePrincipalCollection
        if (obj instanceof SimplePrincipalCollection) {
            SimplePrincipalCollection simplePrincipalCollection = (SimplePrincipalCollection) obj;
            obj = simplePrincipalCollection.getPrimaryPrincipal();
            if (obj instanceof UserEntity) {
                UserEntity user = (UserEntity) obj;
                UserOnlineVo onlineVo = new UserOnlineVo();
                onlineVo.setLastAccess(session.getLastAccessTime());
                onlineVo.setIpAddr(user.getLoginIp());
                onlineVo.setSessionId(session.getId().toString());
                onlineVo.setLastLoginTime(user.getLastLoginTime());
                onlineVo.setTimeout(session.getTimeout());
                onlineVo.setStartTime(session.getStartTimestamp());
                onlineVo.setSessionStatus(false);
                onlineVo.setUsername(user.getUserName());
                return onlineVo;
            }
        }
        return null;
    }
}
