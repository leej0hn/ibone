package com.redscarf.ibone.shiro.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @description:
 * @author: LeeJohn
 * @date: 2018/11/19 18:55
 */
public class UserUtils {
    /**
     * 获取当前登录者对象
     */
    public static PrincipalCollection getPrincipals(){
        try{
            PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
            if (principals != null){
                return principals;
            }
        }catch (UnavailableSecurityManagerException e) {

        }catch (InvalidSessionException e){

        }
        return null;
    }
}
