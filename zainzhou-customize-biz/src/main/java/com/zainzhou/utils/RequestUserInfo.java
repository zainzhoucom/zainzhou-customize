package com.zainzhou.utils;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author : 周振宇
 * Created on 2022/10/26 11:06
 **/
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RequestUserInfo implements Serializable {

    private static final long serialVersionUID = -6284620080170513641L;

    /**
     * 登录用户id
     */
    private String userCode;
    /**
     * 登录用户名称
     */
    private String userName;
}
