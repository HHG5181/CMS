package com.roots.cms.config.shiro;

import com.roots.cms.entity.UserEntity;
import com.roots.cms.service.IPermissionService;
import com.roots.cms.service.IRedisService;
import com.roots.cms.service.IRoleService;
import com.roots.cms.service.IUserService;
import com.roots.cms.utils.Constants;
import com.roots.cms.utils.DateUtils;
import com.roots.cms.utils.ShiroUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.ByteSource;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author admin
 * @ClassName UserRealm.java
 * @Description 自定义Realm
 * @createTime 2020年08月05日 17:25:00
 */
@Component
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private IRedisService redisService;

    @Autowired
    private RedisSessionDAO redisSessionDAO;

    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        UserEntity user = ShiroUtils.getUser();
        Set<String> roles = new HashSet<String>();
        Set<String> perms = new HashSet<String>();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        if (user.isAdmin()) {
            info.addRole("admin");
            info.addStringPermission("*:*:*");
        } else {
            roles = roleService.selectRoleKeyByUserId(user.getUserId());
            perms = permissionService.selectPermsByUserId(user.getUserId());
            // 角色加入AuthorizationInfo认证对象
            info.setRoles(roles);
            // 权限加入AuthorizationInfo认证对象
            info.setStringPermissions(perms);
        }

        return info;
    }

    /**
     * 登陆认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken= (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        UserEntity user = userService.selectByUserName(username);
        if (user == null) {
            throw new UnknownAccountException();
        }
        if (Constants.STATUS_INVALID.equals(user.getStatus())) {
            // 帐号锁定
            throw new LockedAccountException();
        }
        user.setLoginIp(ShiroUtils.getIp());
        user.setLastLoginTime(DateUtils.getNowDate());
        userService.updateUser(user);
        return new SimpleAuthenticationInfo(
                user,
                user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()),
                getName()
        );
    }

    /**
     * 清除认证信息
     *
     * @param userIds 待清除认证信息的userId列表
     */
    public void removeCachedAuthenticationInfo(List<Long> userIds) {
        if (CollectionUtils.isNotEmpty(userIds)) {
            Set<SimplePrincipalCollection> set = getSpcListByUserIds(userIds);
            RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils.getSecurityManager();
            UserRealm realm = (UserRealm) securityManager.getRealms().iterator().next();
            for (SimplePrincipalCollection simplePrincipalCollection : set) {
                realm.clearCachedAuthenticationInfo(simplePrincipalCollection);
            }
        }
    }

    /**
     * 清理缓存权限
     */
    public void clearCachedAuthorizationInfo() {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }

    /**
     * 根据userId 清除当前session存在的用户的权限缓存
     *
     * @param userIds 已经修改了权限的userId列表
     */
    public void clearAuthorizationByUserId(List<Long> userIds) {
        if (CollectionUtils.isNotEmpty(userIds)) {
            Set<SimplePrincipalCollection> set = getSpcListByUserIds(userIds);
            RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils.getSecurityManager();
            UserRealm realm = (UserRealm) securityManager.getRealms().iterator().next();
            for (SimplePrincipalCollection simplePrincipalCollection : set) {
                realm.clearCachedAuthorizationInfo(simplePrincipalCollection);
            }
        }

    }

    /**
     * 根据用户id获取所有spc
     *
     * @param userIds 已经修改了权限的userId
     * @return
     */
    private Set<SimplePrincipalCollection> getSpcListByUserIds(List<Long> userIds) {
        //获取所有session
        Collection<Session> sessions = redisSessionDAO.getActiveSessions();
        //定义返回
        Set<SimplePrincipalCollection> set = new HashSet<>();
        for (Session session : sessions) {
            //获取session登录信息。
            Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (obj instanceof SimplePrincipalCollection) {
                //强转
                SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;
                //判断用户，匹配用户ID。
                obj = spc.getPrimaryPrincipal();
                if (obj instanceof UserEntity) {
                    UserEntity user = (UserEntity) obj;
                    //比较用户ID，符合即加入集合
                    if (user != null && userIds.contains(user.getUserId())) {
                        set.add(spc);
                    }
                }
            }
        }
        return set;
    }

    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(2);
        super.setCredentialsMatcher(hashedCredentialsMatcher);
    }
}
