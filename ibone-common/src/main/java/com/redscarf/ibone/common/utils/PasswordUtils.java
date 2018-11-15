package com.redscarf.ibone.common.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordUtils {
    public static String getMd5PasswordWithSalt(String password,String salt){
        String newPassword = new SimpleHash("MD5", password,  ByteSource.Util.bytes(salt), 1024).toHex();
        return newPassword;
    }

    public static void main(String[] args) {
        matcher("^string.*","stringfffdasasd?><:");
        Pattern pattern = Pattern.compile("^http://ibone-sys-admin.com.*");
        Matcher matcher = pattern.matcher("http://ibone-sys-admin.com:20002/cas?client_name=CasClient");
        System.out.println("args = [" + matcher.matches() + "]");
        System.out.println(PasswordUtils.getMd5PasswordWithSalt("ibone","1521896490002"));
    }

    public static void matcher(String pattern , String string){
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(string);
        System.out.println(string + " matchers = [" + m.matches() + "]");
    }
}
