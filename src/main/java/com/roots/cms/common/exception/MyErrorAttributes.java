package com.roots.cms.common.exception;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * @author admin
 * @ClassName MyErrorAttributes.java
 * @Description TODO
 * @createTime 2020年08月06日 16:47:00
 */
@Component
public class MyErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> map = super.getErrorAttributes(webRequest, includeStackTrace);
        Object attribute = webRequest.getAttribute("ext", 0);
        if (attribute instanceof Map) {
            Map<String, Object> ext = (Map<String, Object>) attribute;
            map.put("ext", ext);
        }
        return map;
    }
}