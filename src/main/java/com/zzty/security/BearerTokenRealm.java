package com.zzty.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.zzty.services.UserService;

public class BearerTokenRealm extends AuthorizingRealm {
	@Autowired
	private UserService userservice;
	
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof BearerAuthenticationToken;

	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		BearerAuthenticationToken bearerAuthenticationToken = (BearerAuthenticationToken) token;
		String bearerToken = bearerAuthenticationToken.getCredentials();
		if (userservice.validateToken(bearerToken)) {
			return new SimpleAuthenticationInfo(bearerAuthenticationToken.getPrincipal(),
					bearerAuthenticationToken.getCredentials(), getName());
		} else {
			return new SimpleAuthenticationInfo(bearerAuthenticationToken.getPrincipal(), "invalid", getName());
		}
	}


	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String token = (String)principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.addRole("user");
		return simpleAuthorizationInfo;
	}

}
