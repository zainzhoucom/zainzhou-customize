package com.zainzhou.mq.message;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author : 周振宇
 * Created on 2022/10/19 15:19
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DemoMqMessage implements Serializable {

    private static final long serialVersionUID = 645258319037181470L;

//    @JSONField(serializeUsing = LocalDateTimeSerializer.class, deserializeUsing = LocalDateTimeDeserializer.class)
    private Date now;

    private String id;
}
