package com.zainzhou.dao.repository;

import com.zainzhou.dao.AbstractEntityRepository;
import com.zainzhou.dao.mapper.RequestLogMapper;
import com.zainzhou.dao.modules.RequestLogDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * @author : 周振宇
 * Created on 2022/10/26 13:18
 **/
@Slf4j
@Repository
public class RequestLogRepository extends AbstractEntityRepository<RequestLogMapper, RequestLogDO> {

}
