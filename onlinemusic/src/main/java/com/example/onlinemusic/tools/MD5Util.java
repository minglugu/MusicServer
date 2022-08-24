package com.example.onlinemusic.tools;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
    //定义一个固定的盐值
    private static final String salt = "1b2i3t4e";


    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    /**
     * 第一次加密 ：模拟前端自己加密，然后传到后端
     * @param inputPass
     * @return
     */
    public static String inputPassToFormPass(String inputPass) {
        String str = ""+salt.charAt(1)+salt.charAt(3) + inputPass
                +salt.charAt(5) + salt.charAt(6);
        return md5(str);
    }

    /**
     * 第2次MD5加密
     * @param fromPass 前端加密过的密码，传给后端进行第2次加密
     * @param salt     后端的当中的盐值
     * @return
     */
    public static String fromPassToDBPass(String fromPass, String salt) {
        String str = ""+salt.charAt(0)+salt.charAt(2) + fromPass + salt.charAt(5)
                + salt.charAt(4);
        return md5(str);
    }

    /**
     * 上面两个函数合到一起进行调用
     * @param inputPass
     * @param saltDB
     * @return
     */
    public static String inputPassToDbPass(String inputPass, String saltDB) {
        String formPass = inputPassToFormPass(inputPass);
        String dbPass = fromPassToDBPass(formPass, saltDB);
        return dbPass;
    }


    public static void main(String[] args) {
        // 分开调用
        System.out.println("对用户输入密码进行第1次加密："+inputPassToFormPass("123456"));

        System.out.println("对用户输入密码进行第2次加密："+fromPassToDBPass(inputPassToFormPass("123456"),
                "1b2i3t4e"));

        // 一起调用
        System.out.println("对用户输入密码进行第2次加密："+inputPassToDbPass("123456", "1b2i3t4e"));
    }
}
