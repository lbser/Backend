package com.lb.backend.controller;

import com.lb.backend.service.LoginService;
import com.lb.backend.util.ChangString;
import com.lb.backend.util.ToJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController

/*
   解决跨域问题，只能spring Framework 4.2和springMVC 4.2以上才能使用
 */
@CrossOrigin
@Api(value = "博客后端接口文档")
@RequestMapping("/home")
public class LoginController {
    public static final String UTF_8 = "UTF-8";
    @Autowired
    LoginService loginService;

    @RequestMapping(value = "/lblogin")
    @ResponseBody
    @ApiOperation(value ="通过手机号和密码进行登录，存储在一个map中",notes ="返回用户的信息或为null")
    public String lblogin(@RequestParam Map<String,String> map, HttpServletRequest request){
        System.out.println("123");
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token;
        HashMap map1;
        boolean hasRole;
        HttpSession session;
        token = new UsernamePasswordToken(map.get("phone").toString(),map.get("encryption").toString());
        map1 = new HashMap();
        map1.put("data", false);
        try {
            subject.login(token);
            map1.put("data", true);
            hasRole = subject.hasRole("yh");
            if (hasRole == true) {
                session = request.getSession();
                session.setAttribute("phone", map.get("phone"));
                System.out.println(hasRole+" 1");
                return ToJson.toJsonArray(loginService.lblogin(map));
            } else {
                System.out.println("321");
                return "联系我们";
            }
        } catch (Exception e) {
            return "0";
        }
    }
    @RequestMapping(value = "")
    @ResponseBody
    @ApiOperation(value = "",notes = "")
    public String register(){
        return "";
    }
}
