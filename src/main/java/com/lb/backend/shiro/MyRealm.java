package com.lb.backend.shiro;

import com.lb.backend.Service.LoginService;
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
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyRealm extends AuthorizingRealm {
    private static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*$");
        Matcher isNum = pattern.matcher(str);
        if(!isNum.matches() ){
            return false;
        }
        return true;
    }
    @Autowired
    LoginService loginService;
    //登录
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        String principal = (String)principals.getPrimaryPrincipal();
        boolean tw  = MyRealm.isNumeric(principal);
        if(tw==true){
            Map map =  new HashMap();
            map.put("phone",principal);
            List<Map> list = (List<Map>) loginService.lblogin(map);
            Map m = list.get(0);
            if(m.get("examine").equals(1)){
                info.addRole("yh");
            }else{
                info.addRole("bu");
            }
        }else{
            Map map =  new HashMap();
            map.put("username",principal);
            List<Map> list = (List<Map>) loginService.lblogin(map);
            Map m = list.get(0);
            if(m.get("examine").equals(1)){
                info.addRole("yh");
            }else{
                info.addRole("te");
            }
        }
        return info;
    }
    //注册
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String  tos = (String) token.getCredentials();
        boolean tl = MyRealm.isNumeric(tos);
        Map<String,String> map = new HashMap<>();
        if(tl==true){
            map.put("phone",tos);
            List<?> list = loginService.lblogin(map);
            String pwd = "";
            String source = "";
            if(list != null){
                Map map1 = (Map) list.get(0);
                pwd = map1.get("saltvalue").toString();
                source = map1.get("encryption").toString();
                Map<String,Object> map2 = new HashMap<>();
                map2.put("saltvalue",pwd);
                map2.put("encryption",source);
                String relname = getName();
                ByteSource bs = new Md5Hash(source);
                SimpleAuthenticationInfo info  = new SimpleAuthenticationInfo(token.getPrincipal(),pwd,bs,relname);
                Map map3 = new HashMap();
                map3.put("phone",token.getPrincipal());
                if(loginService.lblogin(map3).size()==1){
                    return info;
                }
            }else{
                return null;
            }
        }else {
            map.put("username", tos);
            List<?> list = loginService.lblogin(map);
            String pwd = "";
            String source = "";
            if (list != null) {
                Map map1 = (Map) list.get(0);
                pwd = map1.get("saltvalue").toString();
                source = map1.get("encryption").toString();
                Map<String, Object> map2 = new HashMap<>();
                map2.put("saltvalue", pwd);
                map2.put("encryption", source);
                String relname = getName();
                ByteSource bs = new Md5Hash(source);
                SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(token.getPrincipal(), pwd, bs, relname);
                Map map3 = new HashMap();
                map3.put("username", token.getPrincipal());
                if (loginService.lblogin(map3).size() == 1) {
                    return info;
                }
            } else {
                return null;
            }
        }
        return null;
    }
    //证书匹配器
    @PostConstruct
    public void setCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("MD5");
        matcher.setHashIterations(1024);
        setCredentialsMatcher(matcher);
    }
}
