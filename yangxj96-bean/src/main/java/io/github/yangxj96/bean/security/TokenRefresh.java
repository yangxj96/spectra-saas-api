package io.github.yangxj96.bean.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenRefresh implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String token;


}
