package com.zzty.services;

import java.util.List;

import com.zzty.entity.dtos.SessionInfoDTO;
import com.zzty.entity.dtos.UserDTO;

public interface UserService {

	UserDTO getUser(Long id);

	UserDTO createUser(UserDTO user);
	
	public List<UserDTO> getUsers(Integer currentPage, Integer pageSize);
	public SessionInfoDTO validateCreditial(String authValue);
	public boolean validateToken(String token);
	public void updateTokenTime(String token);
}
