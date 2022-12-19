package com.zainzhou.dao;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.zainzhou.utils.RequestUserInfo;
import com.zainzhou.utils.RequestUtil;
import java.lang.reflect.Constructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : 周振宇
 * Created on 2022/10/26 11:12
 **/
@Slf4j
public abstract class AbstractEntityRepository<M extends BaseMapper<T>, T extends BaseEntity> implements
        IEntityRepository<T> {

    protected Log logger = LogFactory.getLog(getClass());
    public static final int DEFAULT_BATCH_SIZE = 100;

    @Autowired
    protected M baseMapper;

    @Override
    public String insert(T entity) {
        appendCommonFieldsValueForInsert(entity);
        baseMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public T assertIdExist(String id) {
        return baseMapper.selectById(id);
    }


    @Override
    public void insertBatch(Collection<T> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            throw new RuntimeException("参数为空");
        }
        entityList.forEach(this::appendCommonFieldsValueForInsert);
        String sqlStatement = getSqlStatement(SqlMethod.INSERT_ONE);
        boolean flag = executeBatch(entityList, DEFAULT_BATCH_SIZE, (sqlSession, entity) -> sqlSession.insert(sqlStatement, entity));
        if (!flag) {
            throw new RuntimeException("记录插入失败");
        }
    }

    @Override
    public List<T> assertIdsExist(List<String> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            throw new RuntimeException("参数为空");
        } else {
            idList = idList.stream().distinct().collect(Collectors.toList());
        }
        List<T> tList = listByIds(idList);
        return tList;
    }

    @Override
    public List<T> listByIds(List<String> idList) {
        return baseMapper.selectBatchIds(idList);
    }

    @Override
    public List<T> list() {
        return null;
    }

    protected void appendCommonFieldsValueForInsert(T entity) {
        if(StringUtils.isBlank(entity.getId())){
            entity.setId(IdWorker.getIdStr());
        }

        entity.setCreateTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());
        entity.setDeleteFlag("0");
//        RequestUserInfo userInfo = RequestUtil.getUserInfo();
        entity.setCreateBy("undefined_code");
        entity.setCreateName("undefined_name");
        entity.setUpdateBy("undefined_code");
        entity.setUpdateName("undefined_name");
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateSelectiveById(T entity) {
        if (Objects.isNull(entity)) {
            throw new RuntimeException("参数为空");
        }
        List<T> entityList = new ArrayList<>();
        entityList.add(entity);
        updateSelectiveByIds(entityList);
    }

    /**
     * 根据ids批量更新相关实体的指定属性的值
     * 【注】不能将属性值更新成null
     *
     * @param entityList 待更新的实体对象集合
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateSelectiveByIds(Collection<T> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            throw new RuntimeException("参数为空");
        }
        entityList.forEach(this::appendCommonFieldsValueForUpdate);
        String sqlStatement = getSqlStatement(SqlMethod.UPDATE_BY_ID);
        boolean flag = executeBatch(entityList, DEFAULT_BATCH_SIZE, (sqlSession, entity) -> {
            MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
            param.put(Constants.ENTITY, entity);
            sqlSession.update(sqlStatement, param);
        });
        if (!flag) {
            throw new RuntimeException("更新失败");
        }
    }

    protected String getSqlStatement(SqlMethod sqlMethod) {
        return SqlHelper.getSqlStatement(currentMapperClass(), sqlMethod);
    }

    protected <E> boolean executeBatch(Collection<E> list, int batchSize, BiConsumer<SqlSession, E> consumer) {
        return SqlHelper.executeBatch(currentModelClass(), this.logger, list, batchSize, consumer);
    }

    protected Class<T> currentMapperClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), 0);
    }

    protected Class<T> currentModelClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), 1);
    }

    protected void appendCommonFieldsValueForUpdate(T entity) {
        entity.setUpdateTime(LocalDateTime.now());
    }

    private T currentModelClassInstance() {
        Class<T> tType = currentModelClass();
        try {
            Constructor<?> constructor = tType.getDeclaredConstructor();
            return (T) BeanUtils.instantiateClass(constructor);
        } catch (Throwable ex) {
            throw new IllegalArgumentException("Cannot instantiate: " + tType, ex);
        }
    }
}
