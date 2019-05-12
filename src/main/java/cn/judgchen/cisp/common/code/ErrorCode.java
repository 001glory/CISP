package cn.judgchen.cisp.common.code;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class ErrorCode {

    /**
     * 错误状态码
     */
    private int code;

    /**
     * 状态消息
     */
    private String message;
}
