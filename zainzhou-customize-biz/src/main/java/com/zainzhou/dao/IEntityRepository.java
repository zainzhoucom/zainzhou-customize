package com.zainzhou.dao;

import java.util.Collection;
import java.util.List;

/**
 * @author : 周振宇
 * Created on 2022/10/26 11:25
 **/
public interface IEntityRepository<T extends BaseEntity>{
    T assertIdExist(String id);

    List<T> assertIdsExist(List<String> idList);

    String insert(T entity);

    void insertBatch(Collection<T> entityList);

    List<T> listByIds(List<String> idList);

    List<T> list();
}
