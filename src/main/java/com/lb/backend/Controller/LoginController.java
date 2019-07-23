package com.lb.backend.Controller;

import com.lb.backend.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@EnableAutoConfiguration
@ComponentScan(basePackages = {"controller"})
public class LoginController {
    public static final String UTF_8 = "UTF-8";
    @Autowired
    LoginService loginService;

    @PostMapping(value = "/lblogin")
    public String lblogin(@RequestParam Map<String,String> map){

        return "";
    }
}
