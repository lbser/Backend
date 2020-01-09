package com.lb.backend.shiro;

import com.lb.backend.service.LoginService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class MyRealm extends AuthorizingRealm {
    @Autowired
    LoginService loginService;
    //判断传入的值是手机号还是用户名
    private static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*$");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        String principal = (String)principalCollection.getPrimaryPrincipal();
        boolean tl = isNumeric(principal);
        LinkedHashMap map;
        List lblogin;
        Map m;
        if (tl==true) {
            map = new LinkedHashMap();
            map.put("phone", principal);
            lblogin = loginService.lblogin(map);
            m = (Map)lblogin.get(0);
            System.out.println(m);
            if (m.get("examine").equals(1)) {
                info.addRole("yh");
            } else {
                info.addRole("bu");
            }
        } else {
            map = new LinkedHashMap();
            map.put("username", principal);
            lblogin = this.loginService.lblogin(map);
            m = (Map)lblogin.get(0);
            System.out.println(m);
            if (m.get("examine").equals(1)) {
                info.addRole("yh");
            } else {
                info.addRole("bu");
            }
        }
        return info;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String us = (String)token.getPrincipal();
        boolean tl = isNumeric(us);
        LinkedHashMap<String, Object> map = new LinkedHashMap();
        List login;
        String pwd;
        String source;
        Map m;
        HashMap map2;
        String relname;
        Md5Hash bs;
        SimpleAuthenticationInfo info;
        HashMap map3;
        if (tl==true) {
            map.put("phone", us);
            System.out.println(map);
            login = this.loginService.lblogin(map);
            System.out.println(login);
            pwd = "";
            source = "";
            System.out.println("shi1");
            if (login == null) {
                return null;
            }
            m = (Map)login.get(0);
            source = m.get("saltvalue").toString();
            pwd = m.get("encryption").toString();
            map2 = new HashMap();
            map2.put("source", source);
            map2.put("pwd", pwd);
            relname = this.getName();
            bs = new Md5Hash(source);
            info = new SimpleAuthenticationInfo(token.getPrincipal(), pwd, bs, relname);
            map3 = new HashMap();
            map3.put("phone", token.getPrincipal());
            if (this.loginService.lblogin(map3).size() == 1) {

                return info;
            }
        } else {
            map.put("username", token.getPrincipal());
            login = this.loginService.lblogin(map);
            pwd = "";
            source = "";
            if (login == null) {
                return null;
            }
            m = (Map)login.get(0);
            source = m.get("saltvalue").toString();
            pwd = m.get("encryption").toString();
            map2 = new HashMap();
            map2.put("source", source);
            map2.put("pwd", pwd);
            relname = this.getName();
            bs = new Md5Hash(source);
            info = new SimpleAuthenticationInfo(token.getPrincipal(), pwd, bs, relname);
            map3 = new HashMap();
            map3.put("username", token.getPrincipal());
            if (this.loginService.lblogin(map3).size() == 1) {
                return info;
            }
        }
        return null;
    }
    @PostConstruct
    public void setCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("MD5");
        matcher.setHashIterations(1024);
        this.setCredentialsMatcher(matcher);
    }
}
