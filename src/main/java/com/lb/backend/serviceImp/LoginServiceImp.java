package com.lb.backend.serviceImp;

import com.lb.backend.dao.Dao;
import com.lb.backend.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LoginServiceImp implements LoginService {

    @Autowired
    Dao dao;
    @Override
    public List<?> lblogin(Map<?, ?> map) {
        System.out.println("loginserviceImp");
        return dao.lblogin(map);
    }
}
