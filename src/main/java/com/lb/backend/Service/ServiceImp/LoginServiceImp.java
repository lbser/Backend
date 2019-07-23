package com.lb.backend.Service.ServiceImp;

import com.lb.backend.Dao.Dao;
import com.lb.backend.Service.LoginService;
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
        return dao.select("lb.huxing.mapper.lblogin",map);
    }

    @Override
    public int insert(Map<?, ?> map) {
        return 0;
    }
}
