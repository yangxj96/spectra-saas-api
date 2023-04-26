package io.github.yangxj96.common.respond;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.Serial;
import java.io.Serializable;

/**
 * 通用响应
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class R implements Serializable {

    @Serial
    @JsonIgnore
    private static final long serialVersionUID = 1L;

    private Integer code;

    private String msg;

    private transient Object data;

    public static R success() {
        setHeader(RStatus.SUCCESS);
        return R.builder().code(RStatus.SUCCESS.getCode()).msg(RStatus.SUCCESS.getMsg()).build();
    }

    public static R success(Object data) {
        setHeader(RStatus.SUCCESS);
        R r = success();
        r.setData(data);
        return r;
    }

    public static R failure() {
        setHeader(RStatus.FAILURE);
        return R.builder().code(RStatus.FAILURE.getCode()).msg(RStatus.FAILURE.getMsg()).build();
    }

    public static R specify(RStatus status) {
        setHeader(status);
        return R.builder().code(status.getCode()).msg(status.getMsg()).build();
    }

    public static R specify(RStatus status, Object data) {
        R r = specify(status);
        r.setData(data);
        return r;
    }

    /**
     * 设置请求头
     *
     * @param status 请求状态
     */
    private static void setHeader(RStatus status) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletResponse response = attributes.getResponse();
            if (response != null) {
                response.setIntHeader(RHttpHeadersExpand.RESULT_CODE, status.getCode());
            }
        }
    }

}
