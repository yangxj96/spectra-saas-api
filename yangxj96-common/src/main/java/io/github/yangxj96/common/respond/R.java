package io.github.yangxj96.common.respond;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 通用响应
 *
 * @author yangxj96
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
        return R.builder().code(RStatus.SUCCESS.getCode()).msg(RStatus.SUCCESS.getMsg()).build();
    }

    public static R failure() {
        return R.builder().code(RStatus.FAILURE.getCode()).msg(RStatus.FAILURE.getMsg()).build();
    }

    public static R specify(RStatus status){
        return R.builder().code(status.getCode()).msg(status.getMsg()).build();
    }

     public static R specify(RStatus status,Object data){
         R r = specify(status);
         r.setData(data);
         return r;
    }

}
