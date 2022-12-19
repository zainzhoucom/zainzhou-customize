package com.zainzhou.reqeust;

import java.io.Serializable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author : 周振宇
 * Created on 2022/09/29 13:44
 **/
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddCacheRequest implements Serializable {

    private static final long serialVersionUID = 4109296145648507423L;

    @Size(max = 128, message = "key max length ${max}")
    @NotBlank(message = "key is not blank")
    private String key;
    @Size(max = 128, message = "value max length ${max}")
    @NotBlank(message = "value is not blank")
    private String value;

    @Max(value = 3600, message = "time out max ${value}")
    @Min(value = 60, message = "time out min ${value}")
    private long timeOut;
}
