package com.redscarf.ibone.shiro.reaml;

import com.redscarf.ibone.sys.api.UserApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class IboneRealm extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(IboneRealm.class);

    private String serverName;

    public IboneRealm(EhCacheManager ehCacheManager,  String serverName){
        this.setCacheManager(ehCacheManager);
        this.serverName = serverName;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("IboneRealm-验证");
        /**
         * 将用户对象保存为身份信息，用于系统获取用户信息
         */
        SimpleAuthenticationInfo saInfo = new SimpleAuthenticationInfo("test", "test", getName());
        return saInfo;
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

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        return info;
    }
}
