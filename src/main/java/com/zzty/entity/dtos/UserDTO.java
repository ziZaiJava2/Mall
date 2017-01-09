package com.zzty.entity.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDTO {
	
	private Long id;
	
	private String nickName;
	
	private String email;
	
	private String gender;
	
	private AddressDTO defaultAddress;
	
	private String password;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public AddressDTO getDefaultAddress() {
		return defaultAddress;
	}

	public void setDefaultAddress(AddressDTO defaultAddress) {
		this.defaultAddress = defaultAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
