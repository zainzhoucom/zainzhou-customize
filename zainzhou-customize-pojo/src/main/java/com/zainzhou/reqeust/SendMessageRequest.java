package com.zainzhou.reqeust;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : 周振宇
 * Created on 2022/10/19 17:19
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendMessageRequest implements Serializable {

    private static final long serialVersionUID = 2063871661111495463L;

    @NotEmpty
    @Size(min = 32, max = 32)
    private String id;
}
