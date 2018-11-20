package com.redscarf.ibone.shiro.reaml;

import com.redscarf.ibone.sys.api.dto.response.UserInfoResponseDTO;
import com.redscarf.ibone.sys.core.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Slf4j
public class IboneRealm extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(IboneRealm.class);

    private String serverName;
    private UserService userService;

    public IboneRealm(EhCacheManager ehCacheManager,UserService userService,  String serverName){
        logger.info("IboneRealm constructor...");
        //设置支付的Token类
        setAuthenticationTokenClass(UsernamePasswordToken.class);
        this.setCacheManager(ehCacheManager);
        this.serverName = serverName;
        this.userService = userService;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("IboneRealm-验证");
        /**
         * 将用户对象保存为身份信息，用于系统获取用户信息
         */
        String username = "ibone";
        String serverName = "ibone-sys-admin";
        UserInfoResponseDTO userModel = userService.getUserDetailByNameAndServerName(username, serverName);

        List<Object> principals = CollectionUtils.asList(new Object[]{userModel});
        SimplePrincipalCollection principalCollection = new SimplePrincipalCollection(principals, this.getName());

        return new SimpleAuthenticationInfo(principalCollection,token.getCredentials());

    }

    /**
     * 权限认证，为当前登录的Subject授予角色和权限
     *  ：本例中该方法的调用时机为需授权资源被访问时
     *  ：并且每次访问需授权资源时都会执行该方法中的逻辑，这表明本例中默认并未启用AuthorizationCache
     *  ：如果连续访问同一个URL（比如刷新），该方法不会被重复调用，Shiro有一个时间间隔（也就是cache时间，在ehcache-shiro.xml中配置），超过这个时间间隔再刷新页面，该方法会被执行
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("--加载Shiro权限认证--");

        UserInfoResponseDTO userModel = principals.oneByType(UserInfoResponseDTO.class);
        Set<String> roles = userModel.getRoles();
        Set<String> permissions = userModel.getPermissions();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        if(roles != null && !roles.isEmpty()){
            Iterator<String> iterator = roles.iterator();
            while (iterator.hasNext()){
                info.addRole(iterator.next());
            }
        }

        if(permissions != null && !permissions.isEmpty()){
            Iterator<String> iterator = permissions.iterator();
            while (iterator.hasNext()){
                info.addStringPermission(iterator.next());
            }
        }

        return info;
    }
}
