package com.zzty.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zzty.entity.AddressEntity;
import com.zzty.entity.UserEntity;
import com.zzty.entity.dtos.AddressDTO;
import com.zzty.entity.dtos.UserDTO;
import com.zzty.entity.repositories.UserRepository;
import com.zzty.exceptions.NotFoundExcpetion;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	private AddressDTO convertToDTO(AddressEntity entity) {
		AddressDTO addressDTO = new AddressDTO();
		
		addressDTO.setId(entity.getId());
		addressDTO.setAddress(entity.getAddress());
		addressDTO.setTelephone(entity.getTelephone());
		
		return addressDTO;
	}

	private UserDTO convertToDTO(UserEntity entity) {
		UserDTO dto = new UserDTO();
		
		dto.setId(entity.getId());
		dto.setEmail(entity.getEmail());
		dto.setGender(entity.getGender());
		dto.setNickName(entity.getNickName());
		dto.setPassword(entity.getPassword());
		
		for(AddressEntity address : entity.getAddressEntityList()) {
			if (address.isDefaults()) {
				AddressDTO addressDTO = convertToDTO(address);
				dto.setDefaultAddress(addressDTO);
			}
		}
		
		return dto;
	}
	
	private UserEntity convertToEntity(UserDTO dto) {
		UserEntity user = new UserEntity();
		
		user.setEmail(dto.getEmail());
		user.setId(dto.getId());
		user.setGender(dto.getGender());
		user.setNickName(dto.getNickName());
		user.setPassword(dto.getPassword());
		
		return user;
	}
	
	private AddressEntity convertToEntity(AddressDTO dto, UserEntity user, boolean isDefault) {
		AddressEntity entity = new AddressEntity();
		
		entity.setUserEntity(user);
		entity.setAddress(dto.getAddress());
		entity.setTelephone(dto.getTelephone());
		entity.setDefaults(isDefault);
		
		
		
		return entity;
	}
	
	public UserDTO getUser(Long id) {
		UserEntity userEntity = userRepository.getUser(id);
		if (userEntity == null) {
			throw new NotFoundExcpetion();
		}
		UserDTO userDTO = convertToDTO(userEntity);
		return userDTO;
	}


	@Override
	@Transactional
	public UserDTO createUser(UserDTO user) {
		UserEntity userEntity = convertToEntity(user);
		userEntity = userRepository.createUser(userEntity);
		
		if (user.getDefaultAddress() != null) {
			AddressEntity addressEntity =convertToEntity(user.getDefaultAddress(), userEntity, true);
			userRepository.createAddress(addressEntity);
			userEntity.getAddressEntityList().add(addressEntity);
		}
		
		
		return getUser(userEntity.getId());
	}
	
}
