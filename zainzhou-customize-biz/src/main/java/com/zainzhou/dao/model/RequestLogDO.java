package com.zainzhou.dao.model;

import com.baomidou.mybatisplus.annotation.*;
import java.time.LocalDateTime;
import lombok.*;

import java.io.Serializable;

/**
 * 接口请求记录表实体类
 * @author 周振宇
 * @since 2022-10-27
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName("request_log")
public class RequestLogDO implements Serializable {

    private static final long serialVersionUID = 769720559916884473L;

    /**
     * 唯一ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 请求用户ID
     */
    @TableField("user_code")
    private String userCode;

    /**
     * 请求用名称
     */
    @TableField("user_name")
    private String userName;

    /**
     * 请求发起时间戳
     */
    @TableField("request_time")
    private LocalDateTime requestTime;

    /**
     * 请求地址
     */
    @TableField("request_url")
    private String requestUrl;

    /**
     * 返回code
     */
    @TableField("response_code")
    private String responseCode;

    /**
     * 是否成功标识1：成功，0：失败
     */
    @TableField("success_flag")
    private String successFlag;

    /**
     * 接口耗时
     */
    @TableField("elapsed")
    private Integer elapsed;

    /**
     * 请求参数
     */
    @TableField("request_body")
    private String requestBody;

    /**
     * 返回内容
     */
    @TableField("response_body")
    private String responseBody;

    /**
     * 返回消息
     */
    @TableField("response_msg")
    private String responseMsg;


}

