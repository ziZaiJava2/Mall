package com.zzty.security;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.subject.PrincipalCollection;

public class BearerAuthenticationInfo implements AuthenticationInfo{

	private String credentials;
	private PrincipalCollection principals;
	
	public BearerAuthenticationInfo(PrincipalCollection principals, String credentials){
		this.principals = principals;
		this.credentials = credentials;
	}
	
	@Override
	public PrincipalCollection getPrincipals() {
		return this.principals;
	}

	@Override
	public String getCredentials() {
		return this.credentials;
	}

}
