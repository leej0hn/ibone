package com.redscarf.ibone.shiro;

import com.redscarf.ibone.configuration.IboneConfiguration;
import com.redscarf.ibone.shiro.listener.IboneSessionListener;
import com.redscarf.ibone.shiro.reaml.IboneRealm;
import com.redscarf.ibone.shiro.session.IboneSessionDao;
import com.redscarf.ibone.shiro.session.IboneSessionFactory;
import com.redscarf.ibone.shiro.session.IboneSessionTicketStore;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.DispatcherType;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro集成Cas配置
 *
 */
@Slf4j
@Configuration
public class ShiroConfiguration {



    @Bean
    IboneSessionTicketStore getSessionTicketStore(StringRedisTemplate stringRedisTemplate, IboneConfiguration jboneConfiguration){
        IboneSessionTicketStore sessionTicketStore = new IboneSessionTicketStore();
        sessionTicketStore.setRedisTemplate(stringRedisTemplate);
        sessionTicketStore.setTimeout(jboneConfiguration.getCas().getClientSessionTimeout());
        return sessionTicketStore;
    }


    @Bean
    public IboneRealm getIboneCasRealm(EhCacheManager ehCacheManager,  IboneConfiguration iboneConfiguration){
        IboneRealm realm = new IboneRealm(ehCacheManager, iboneConfiguration.getSys().getServerName());
        return realm;
    }

    @Bean
    public EhCacheManager getEhCacheManager() {
        EhCacheManager em = new EhCacheManager();
        em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return em;
    }

    /**
     * 注册DelegatingFilterProxy（Shiro）
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        //  该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));

        return filterRegistration;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(IboneRealm iboneCasRealm,DefaultWebSessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(iboneCasRealm);
        //用户授权/认证信息Cache, 采用EhCache 缓存
        securityManager.setCacheManager(getEhCacheManager());
//        securityManager.setSubjectFactory(new Pac4jSubjectFactory());
        securityManager.setSessionManager(sessionManager);

        return securityManager;
    }

    @Bean(name = "sessionManager")
    public DefaultWebSessionManager getDefaultWebSessionManager(SessionListener sessionListener, SessionDAO sessionDao, SessionFactory sessionFactory,IboneConfiguration iboneConfiguration,SimpleCookie cookie){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(iboneConfiguration.getCas().getClientSessionTimeout());
        sessionManager.setSessionValidationSchedulerEnabled(false);
        sessionManager.setSessionListeners(Arrays.asList(sessionListener));
        sessionManager.setSessionDAO(sessionDao);
        sessionManager.setSessionFactory(sessionFactory);
        sessionManager.setSessionIdCookie(cookie);
        return sessionManager;
    }

    @Bean(name = "sessionDao")
    public SessionDAO getSessionDao(StringRedisTemplate redisTemplate, IboneSessionTicketStore sessionTicketStore){
        IboneSessionDao sessionDao = new IboneSessionDao(redisTemplate);
        sessionDao.setSessionTicketStore(sessionTicketStore);
        return sessionDao;
    }

    @Bean
    public SimpleCookie getCookie(){
        SimpleCookie cookie = new SimpleCookie();
        cookie.setName("jbone.session.id");
        return cookie;
    }


    @Bean(name = "sessionListener")
    public SessionListener getSessionListener(){
        return new IboneSessionListener();
    }

    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(){
        return new IboneSessionFactory();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 加载shiroFilter权限控制规则
     */
    private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean,IboneConfiguration iboneConfiguration){
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String,String>();


        //添加ibone.cas的配置规则
        if(iboneConfiguration.getCas().getFilterChainDefinition() != null){
            filterChainDefinitionMap.putAll(iboneConfiguration.getCas().getFilterChainDefinition());
        }
        String common = filterChainDefinitionMap.get("/**");
        filterChainDefinitionMap.put("/**", "security" + (StringUtils.isNotBlank(common) ? ("," + common) : ""));
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    }

    /**
     * ShiroFilter
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager, IboneConfiguration iboneConfiguration, StringRedisTemplate redisTemplate, DefaultWebSessionManager sessionManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // SecurityManager，Shiro安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        shiroFilterFactoryBean.setLoginUrl(iboneConfiguration.getCas().getEncodedLoginUrl());
        shiroFilterFactoryBean.setSuccessUrl(iboneConfiguration.getCas().getSuccessUrl());
        shiroFilterFactoryBean.setUnauthorizedUrl(iboneConfiguration.getCas().getUnauthorizedUrl());

        loadShiroFilterChain(shiroFilterFactoryBean, iboneConfiguration);
        return shiroFilterFactoryBean;
    }

}