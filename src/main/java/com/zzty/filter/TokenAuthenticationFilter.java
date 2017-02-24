package com.zzty.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.AccessControlFilter;

import com.zzty.security.BearerAuthenticationToken;

public class TokenAuthenticationFilter extends AccessControlFilter{

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String uri = httpRequest.getRequestURI();
		String token = httpRequest.getHeader("token");
		if (!"/mall/login".equals(uri)) {
			BearerAuthenticationToken bearerAuthenticationToken = new BearerAuthenticationToken(token, token);
			try {
				getSubject(request, response).login(bearerAuthenticationToken);
			} catch (Exception e) {
				e.printStackTrace();
				httpResponse.sendError(401, "invalid token");
				return false;
			}
		}
		return true;
	}

}
