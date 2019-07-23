package com.lb.backend.Dao.daoSupport;

import com.lb.backend.Dao.Dao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("daoSupport")
public class DaoSupport implements Dao {

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<?> select(String str, Map<?, ?> map) {
        return sqlSessionTemplate.select(str,map);
    }

    @Override
    public int update(String str, Map<?, ?> map) {
        return sqlSessionTemplate.update(str,map);
    }

    @Override
    public int insert(String str, Map<?, ?> map) {
        return sqlSessionTemplate.insert(str,map);
    }

    @Override
    public int delete(String str, Map<?, ?> map) {
        return sqlSessionTemplate.delete(str,map);
    }
}
