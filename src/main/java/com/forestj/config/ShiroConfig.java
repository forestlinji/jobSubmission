package com.forestj.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
    public SecurityManager getSecurityManager(CustomRealm realm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        securityManager.setCacheManager(cacheManager());
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }
    @Bean
    public CustomRealm getRealm() {
        return new CustomRealm();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }


    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        //创建过滤器工厂
        ShiroFilterFactoryBean filterFactory = new ShiroFilterFactoryBean();
        //设置安全管理器
        filterFactory.setSecurityManager(securityManager);
        filterFactory.setLoginUrl("/errors");//跳转url地址
        //通用配置（跳转登录页面，为授权跳转的页面）
        Map<String,String> filterMap = new LinkedHashMap<>();
        filterMap.put("/user/login","anon");
        filterMap.put("/user/signup","anon");
        filterMap.put("/exam/getAll","anon");
        filterMap.put("/user/getExam","anon");
        filterMap.put("/work/upload","anon");
        filterMap.put("/swagger-ui.html", "anon");
        filterMap.put("/swagger/**", "anon");
        filterMap.put("/swagger-resources/**", "anon");
        filterMap.put("/v2/**", "anon");
        filterMap.put("/webjars/**", "anon");
        filterMap.put("/configuration/**", "anon");

        filterMap.put("/**","authc");

        filterFactory.setFilterChainDefinitionMap(filterMap);

        return filterFactory;
    }

    /**
     * shiro-redis相关配置
     */
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.password}")
    private String redisPwd;

    public RedisManager redisManager(){
        RedisManager redisManager=new RedisManager();
        redisManager.setHost(host+":"+port);
        redisManager.setPassword(redisPwd);
        return redisManager;
    }
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO sessionDAO = new RedisSessionDAO();
        sessionDAO.setRedisManager(redisManager());
        return sessionDAO;
    }
    /**
     * 会话管理器
     */
    public DefaultWebSessionManager sessionManager() {
        CustomSessionManager sessionManager = new CustomSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        return sessionManager;
    }

    /**
     * 缓存管理器
     */
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }
}
