package com.lb.backend.Dao;

import java.util.List;
import java.util.Map;

public interface Dao {
    public List<?> select(String str,Map<?,?> map);

    public int update(String str,Map<?,?> map);

    public int insert(String str,Map<?,?> map);

    public int delete(String str,Map<?,?> map);
}
