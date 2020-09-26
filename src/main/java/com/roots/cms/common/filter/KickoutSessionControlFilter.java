package com.roots.cms.common.filter;

import com.alibaba.fastjson.JSON;
import com.roots.cms.entity.UserEntity;
import com.roots.cms.utils.Constants;
import lombok.Setter;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author admin
 * @ClassName KickoutSessionControlFilter.java
 * @Description 提出用户
 * @createTime 2020年08月07日 19:47:00
 */
@Setter
public class KickoutSessionControlFilter extends AccessControlFilter {

    //提出后跳转地址
    private String kickoutUrl;

    /**
     * 用户session的属性，是否踢出
     */
    private String kickoutSessionAttrName = "kickout";
    private boolean kickoutAfter;
    //最大会话数
    private int maxSession = 5;

    private SessionManager sessionManager;
    private Cache<String, Deque<Serializable>> cache;

    /**
     * 设置Cache的key前缀
     * @param cacheManager
     */
    public void setCacheManager(CacheManager cacheManager) {
        this.cache = cacheManager.getCache(Constants.SHIRO_REDIS_CACHE_NAME);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            //如果没有登录，直接进行之后的流程
            return true;
        }


        Session session = subject.getSession();
        UserEntity user = (UserEntity) subject.getPrincipal();
        String username = user.getUserName();
        Serializable sessionId = session.getId();

        //读取缓存   没有就存入
        Deque<Serializable> deque = cache.get(username);

        //如果此用户没有session队列，也就是还没有登录过，缓存中没有
        //就new一个空队列，不然deque对象为空，会报空指针
        if (deque == null) {
            deque = new LinkedList<Serializable>();
        }

        //如果队列里没有此sessionId，且用户没有被踢出；放入队列
        if (!deque.contains(sessionId) && session.getAttribute(kickoutSessionAttrName) == null) {
            //将sessionId存入队列
            deque.push(sessionId);
            //将用户的sessionId队列缓存
            cache.put(username, deque);
        }

        //如果队列里的sessionId数超出最大会话数，开始踢人
        while (deque.size() > maxSession) {
            Serializable kickoutSessionId = null;
            if (kickoutAfter) {
                //如果踢出后者
                kickoutSessionId = deque.removeFirst();
            } else { //否则踢出前者
                kickoutSessionId = deque.removeLast();
            }
            //踢出后再更新下缓存队列
            cache.put(username, deque);

            //获取被踢出的sessionId的session对象
            Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
            if (kickoutSession != null) {
                //设置会话的kickout属性表示踢出了
                kickoutSession.setAttribute(kickoutSessionAttrName, true);
            }
        }

        //如果被踢出了，直接退出，重定向到踢出后的地址
        if (session.getAttribute(kickoutSessionAttrName) != null && (Boolean) session.getAttribute(kickoutSessionAttrName)) {
            //退出登录
            subject.logout();
            saveRequest(request);

            Map<String, String> resultMap = new HashMap<String, String>();
            //判断是不是Ajax请求
            if ("XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"))) {
                resultMap.put("user_status", "300");
                resultMap.put("message", "您已经在其他地方登录，请重新登录！");
                //输出json串
                out(response, resultMap);
            } else {
                //重定向
                WebUtils.issueRedirect(request, response, kickoutUrl);
            }
            return false;
        }
        return true;
    }

    private static void out(ServletResponse hresponse, Map<String, String> resultMap) throws IOException {
        hresponse.setCharacterEncoding("UTF-8");
        try (PrintWriter out = hresponse.getWriter();) {
            out.println(JSON.toJSONString(resultMap));
            out.flush();
        } catch (Exception e) {
            throw e;
        }
    }
}
