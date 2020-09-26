package com.roots.cms.utils;

import com.roots.cms.entity.UserEntity;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @author admin
 * @ClassName PasswordUtils.java
 * @Description 密码工具类
 * @createTime 2020年08月05日 19:55:00
 */

public class PasswordUtils {

    private static final RandomNumberGenerator RANDOM_NUMBER_GENERATOR = new SecureRandomNumberGenerator();
    private static final String ALGORITHM_NAME = "md5";
    private static final int HASH_ITERATIONS = 2;

    public static void encryptPassword(UserEntity user) {
        user.setSalt(ShiroUtils.randomSalt());
        String newPassword = new SimpleHash(ALGORITHM_NAME, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), HASH_ITERATIONS).toHex();
        user.setPassword(newPassword);
    }

    public static String getPassword(UserEntity user) {
        return new SimpleHash(ALGORITHM_NAME, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), HASH_ITERATIONS).toHex();
    }

    public static void main(String[] args) {
        UserEntity user = new UserEntity();
        user.setUserName("admin");
        user.setPassword("123456");
        encryptPassword(user);
        System.out.println(user.getPassword());

        System.out.println(getPassword(user));
    }
}
