package com.zzty.security;

import org.apache.shiro.authc.AuthenticationToken;

public class BearerAuthenticationToken implements AuthenticationToken {

	private String principal;
	private String credentials;

	public BearerAuthenticationToken(String principal, String credentials) {
		this.principal = principal;
		this.credentials = credentials;
	}

	@Override
	public String getPrincipal() {
		return this.principal;
	}

	@Override
	public String getCredentials() {
		return this.credentials;
	}

}
