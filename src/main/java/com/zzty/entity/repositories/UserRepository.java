package com.zzty.entity.repositories;

import com.zzty.entity.AddressEntity;
import com.zzty.entity.UserEntity;

public interface UserRepository {
	UserEntity getUser(Long id);

	UserEntity createUser(UserEntity userEntity);

	AddressEntity createAddress(AddressEntity addressEntity);
}
