package com.zzty.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.zzty.services.UserService;

public class AuthenticationFilter implements Filter{

	WebApplicationContext context;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		context=  WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext());
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		String uri = httpRequest.getRequestURI();
		UserService userService = (UserService) context.getBean("userServiceImpl");
		String token = httpRequest.getHeader("token");
		if((!"/mall/users".equals(uri) && !"POST".equals(httpRequest.getMethod())) || (!"/mall/login".equals(uri) && !"GET".equals(httpRequest.getMethod()))){
			if(token!=null && !"".equals(token)){
				boolean isTokenValid = userService.validateToken(token);
				if(!isTokenValid){
					httpResponse.sendError(401, "invalid token");
				}
			}else{
				httpResponse.sendError(401, "invalid token");
			}
		}
		
		userService.updateTokenTime(token);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
