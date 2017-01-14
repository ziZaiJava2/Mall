package com.zzty.entity.repositories;

import java.util.List;

import com.zzty.entity.AddressEntity;
import com.zzty.entity.UserEntity;

public interface UserRepository {
	UserEntity getUser(Long id);
	UserEntity getUserByName(String userName);
	UserEntity createUser(UserEntity userEntity);

	AddressEntity createAddress(AddressEntity addressEntity);
	
	List<UserEntity> getAllUsers();
	List<UserEntity> getPagedUsers(Integer currentPage, Integer pageSize );
}
