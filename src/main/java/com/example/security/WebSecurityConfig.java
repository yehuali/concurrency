package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * http://blog.didispace.com/xjf-spring-security-2/
 * @EnableWebSecurity注解使得SpringMVC集成了Spring Security的web安全支持
 * WebSecurityConfig配置类同时集成了WebSecurityConfigurerAdapter,重写了其中的特定方法，用于自定义Spring Security配置
 * @EnableWebSecurity完成的工作便是加载了WebSecurityConfiguration，AuthenticationConfiguration这两个核心配置类
 * --->spring security的职责划分为了配置安全信息，配置认证信息两部分
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 定义了哪些URL路径应该被拦截
     * ”/“, “/home”允许所有人访问，”/login”作为登录入口，也被允许访问，而剩下的”/hello”则需要登陆后才可以访问
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    /**
     * 在内存中配置一个用户，admin/admin分别是用户名和密码，这个用户拥有USER角色
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("admin").password("admin").roles("USER");
    }
}
