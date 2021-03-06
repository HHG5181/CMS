package com.roots.cms.service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author admin
 * @Description TODO
 * @createTime 2020年08月06日 17:11:00
 */
public interface IRedisService {

    <T> void set(String key, T value);

    <T> void set(String key, T value, long expire, TimeUnit timeUnit);

    <T> T get(String key);

    boolean expire(String key, long expire);

    void del(String key);

    void delBatch(Set<String> keys);

    void delBatch(String keyPrefix);

    <T> void setList(String key, List<T> list);

    <T> void setList(String key, List<T> list, long expire, TimeUnit timeUnit);

    <T> List<T> getList(String key, Class<T> clz);

    boolean hasKey(String key);

    long getExpire(String key);

    Set<String> keySet(String keyPrefix);
}
