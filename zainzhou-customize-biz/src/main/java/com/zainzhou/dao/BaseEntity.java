package com.zainzhou.dao;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : 周振宇
 * Created on 2022/10/26 11:13
 **/
@Getter
@Setter
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -1982952462173837990L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private String id;

    /**
     * !0是 0否
     */
    @TableField("delete_flag")
    private String deleteFlag;

    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;

    @TableField("create_name")
    private String createName;

    /**
     * 创建日期
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    @TableField("update_by")
    private String updateBy;

    @TableField("update_name")
    private String updateName;
    /**
     * 更新日期
     */
    @TableField("update_time")
    private LocalDateTime updateTime;
}
