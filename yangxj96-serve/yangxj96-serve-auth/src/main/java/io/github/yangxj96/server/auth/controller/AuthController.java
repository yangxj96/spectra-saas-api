package io.github.yangxj96.server.auth.controller;

import io.github.yangxj96.bean.security.Token;
import io.github.yangxj96.common.respond.R;
import io.github.yangxj96.server.auth.domain.Login;
import io.github.yangxj96.starter.security.store.TokenStore;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 认证控制器
 */
@Slf4j
@RestController
@RequestMapping
public class AuthController {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private TokenStore tokenStore;

    /**
     * 用户名密码方式登录
     *
     * @param param 登录参数
     * @return 登录结果
     */
    @PostMapping(value = "/login")
    public Token login(@RequestBody Login param) {
        Token token = null;
        // 构建后认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(param.getUsername(), param.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 判断是否登录成功,进行创建token且响应
        try {
            if (authenticate.isAuthenticated()) {
                token = tokenStore.create(authenticate);
                R.success();
            } else {
                R.failure();
            }
        } catch (Exception e) {
            R.failure();
        }
        return token;
    }

    /**
     * 刷新token
     *
     * @param token token
     * @return 刷新后的token信息
     */
    @PostMapping("/refresh")
    public Token refresh(String token) {
        try {
            Token refresh = tokenStore.refresh(token);
            R.success();
            return refresh;
        } catch (Exception e) {
            R.failure();
            return null;
        }
    }

    /**
     * 通用退出方法
     *
     * @param token token
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/logoff")
    public void logout(String token) {
        try {
            tokenStore.remove(token);
            R.success();
        } catch (Exception e) {
            R.failure();
        }
    }


    /**
     * 检查token状态
     *
     * @param token token
     * @return token信息
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/check_token")
    public Token checkToken(String token) {
        return null;
    }

}
