package io.github.yangxj96.starter.security.filter;

import io.github.yangxj96.starter.security.store.TokenStore;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class UserAuthorizationFilter extends BasicAuthenticationFilter {

    private final TokenStore tokenStore;

    public UserAuthorizationFilter(AuthenticationManager authenticationManager, TokenStore tokenStore) {
        super(authenticationManager);
        this.tokenStore = tokenStore;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authorization = request.getHeader("Authorization");
		if (StringUtils.isNotEmpty(authorization)) {
			// 放入security上下文,就可以进行认证了
			SecurityContextHolder.getContext().setAuthentication(tokenStore.read(authorization));
		}
		super.doFilterInternal(request, response, chain);
    }
}
