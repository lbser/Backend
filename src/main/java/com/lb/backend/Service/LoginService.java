package com.lb.backend.Service;

import java.util.List;
import java.util.Map;

public interface LoginService {
    //登陆
    public List<?> lblogin(Map<?,?> map);
    //注册
    public int insert(Map<?,?> map);
}
