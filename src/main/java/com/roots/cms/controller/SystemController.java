package com.roots.cms.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.roots.cms.entity.UserEntity;
import com.roots.cms.service.IConfigService;
import com.roots.cms.service.IUserService;
import com.roots.cms.utils.*;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author admin
 * @ClassName SystemController.java
 * @Description TODO
 * @createTime 2020年08月05日 18:39:00
 */
@Controller
@Slf4j
@Api(value = "系统管理", description = "系统管理Api")
public class SystemController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IConfigService configService;

    @Autowired
    private Producer producer;

    /**
     * 系统首页
     * @param map
     * @return
     */
    @GetMapping("/index")
    public String index(ModelMap map) {
        //获取身份信息
        UserEntity user = ShiroUtils.getUser();
        map.put("user", user);
        return "/admin/index.html";
    }

    /**
     * 获取验证码图片
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/verify")
    @ResponseBody
    public ResponseVo getCaptchaCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        //生成验证码文本
        String capText = producer.createText();
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        //利用生成的字符串构建图片
        BufferedImage bi = producer.createImage(capText);
        //将图片转换为Base64
        BASE64Encoder encoder = new BASE64Encoder();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(bi, "jpg", out);
        byte[] bytes = out.toByteArray();
        //转为base64字符串
        String jpg_base64 = encoder.encodeBuffer(bytes).trim();
        //删除\r\n
        jpg_base64 = jpg_base64.replaceAll("\n", "").replaceAll("\r", "");
        Map map = new HashMap<>();
        map.put("image", "data:image/jpg;base64," + jpg_base64);
        map.put("validateCode", capText);
        return ResultUtils.success("生成验证码成功", map);
    }

    /**
     * 登陆
     * @return
     */
    @GetMapping("/login")
    public String login(Model model) {
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return "/admin/index.html";
        }
        getConfig(model);
        return "admin/login.html";
    }

    /**
     * 提交登陆
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public ResponseVo login(HttpServletRequest request, String username, String password, String verify) {
        //验证验证码
        String code = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (StringUtils.isNoneBlank(verify, code) && StringUtils.equalsAnyIgnoreCase(verify, code)) {
            //验证码通过
        } else {
            return ResultUtils.error("验证码错误");
        }
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
        } catch (LockedAccountException e) {
            token.clear();
            return ResultUtils.error("用户已经被锁定不能登陆");
        } catch (ArithmeticException e) {
            token.clear();
            return ResultUtils.error("账户或密码错误");
        }

        //更新最后登录时间
        UserEntity userEntity = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        userEntity.setLastLoginTime(DateUtils.getNowDate());
        userEntity.setLoginIp(ShiroUtils.getIp());
        userService.updateUser(userEntity);
        return ResultUtils.success("登录成功!", "/index");
    }

    /**
     * 登出
     * @return
     */
    @GetMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            String userName = ((UserEntity) SecurityUtils.getSubject().getPrincipal()).getUserName();
            Serializable sessionId = SecurityUtils.getSubject().getSession().getId();
            userService.kickout(sessionId, userName);
        }
        subject.logout();
        return "/admin/login.html";
    }

    private void getConfig(Model model) {
        Map<String, String> map = configService.selectAll();
        model.addAttribute("config", map);
    }

    /**
     * 踢出
     * @param model
     * @return
     */
    @GetMapping("/kickout")
    public String kicout(Model model) {
        getConfig(model);
        return "";
    }

}
