package com.zainzhou.dao.modules;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zainzhou.dao.BaseEntity;
import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author : 周振宇
 * Created on 2022/10/26 13:16
 **/
@Getter
@Setter
@ToString
@Builder
@TableName("")
public class RequestLogDO extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1506190103976530377L;
}
