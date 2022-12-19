package com.zainzhou.event.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author : 周振宇
 * Created on 2022/10/28 10:33
 **/
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AsyncTransactionModel implements Serializable {

    private static final long serialVersionUID = -6615465750804623496L;

    private String m1Id;
    private String m1Context;

    private String m2Id;
    private String m2Context;

    private String m3Id;
    private String m3Context;

    private long incr;
}
