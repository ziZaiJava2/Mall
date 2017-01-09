package com.zzty.services;

import com.zzty.entity.dtos.UserDTO;

public interface UserService {

	UserDTO getUser(Long id);

	UserDTO createUser(UserDTO user);

}
