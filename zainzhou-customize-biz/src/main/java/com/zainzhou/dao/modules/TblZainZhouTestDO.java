package com.zainzhou.dao.modules;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zainzhou.dao.BaseEntity;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author : 周振宇
 * Created on 2022/10/27 17:57
 **/
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName("tbl_zainzhou_test")
public class TblZainZhouTestDO extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -898600471964476597L;

    @TableField("context")
    private String context;
}
