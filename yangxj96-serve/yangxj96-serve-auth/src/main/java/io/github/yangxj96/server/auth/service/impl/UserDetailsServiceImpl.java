package io.github.yangxj96.server.auth.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * 用户名密码登录
 *
 * @author 杨新杰
 * @date 2021/11/30 14:02
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("登录认证");
        return null;
//		if (StringUtils.isEmpty(username)){
//			throw new NullPointerException("用户名为空");
//		}
//		User user = userService.getOne(
//			new LambdaQueryWrapper<User>()
//				.eq(User::getUsername, username)
//				.last("LIMIT 1")
//		);
//		if (null == user){
//			throw new UsernameNotFoundException("用户不存在");
//		}
//		user.setAuthorities(userService.getAuthorities(user.getId()));
//		return user;
    }

}
