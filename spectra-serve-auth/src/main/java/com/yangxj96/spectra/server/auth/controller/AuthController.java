package com.yangxj96.spectra.server.auth.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.exception.NotLoginException;
import com.yangxj96.spectra.server.auth.pojo.vo.AuthLogin;
import com.yangxj96.spectra.server.auth.service.AuthService;
import com.yangxj96.spectra.starter.db.entity.security.Token;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.LoginException;

/**
 * 认证控制器
 */
@Slf4j
@RestController
@RequestMapping
public class AuthController {

    private static final String PREFIX = "[AuthController]: ";

    @Resource
    private AuthService bindService;

    @Resource
    private HttpServletRequest request;

    /**
     * 用户名密码方式登录
     *
     * @param param 登录参数
     * @return 登录结果
     */
    @SaIgnore
    @PostMapping(value = "/login")
    public Token login(@Validated @RequestBody AuthLogin param) throws LoginException {
        log.atInfo().log("{}用户:{}开始登录,输入的密码为:{}", PREFIX, param.getUsername(), param.getPassword());
        return bindService.login(param.getUsername(), param.getPassword());
    }

    /**
     * 检查token状态
     *
     * @return token信息
     */
    @SaCheckLogin
    @PostMapping("/check_token")
    public Token checkToken() throws LoginException {
        log.atInfo().log("{}登录检查,token:{}", PREFIX, request.getHeader("Authorization"));
        return bindService.check();
    }

    /**
     * 通用退出方法
     */
    @SaCheckLogin
    @PostMapping("/logoff")
    public void logout() {
        try {
            log.atInfo().log("{}用户登出,token:{}", PREFIX, request.getHeader("Authorization"));
            bindService.logout();
        } catch (Exception e) {
            throw new NotLoginException("退出异常", "", "");
        }
    }


}
