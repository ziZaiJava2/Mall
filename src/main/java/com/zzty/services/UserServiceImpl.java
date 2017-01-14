package com.zzty.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zzty.entity.AddressEntity;
import com.zzty.entity.UserEntity;
import com.zzty.entity.dtos.AddressDTO;
import com.zzty.entity.dtos.SessionInfoDTO;
import com.zzty.entity.dtos.UserDTO;
import com.zzty.entity.repositories.UserRepository;
import com.zzty.exceptions.LoginFailedException;
import com.zzty.exceptions.NotFoundExcpetion;

@Service
public class UserServiceImpl implements UserService {
	
	public static Map<String, Date> tokenMap = new ConcurrentHashMap<String, Date>();

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
		user.setPassword(DigestUtils.sha1Hex(dto.getPassword()));
		
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

	public List<UserDTO> getUsers(Integer currentPage, Integer pageSize){
		List<UserEntity> users;
		List<UserDTO> userDTOList = new ArrayList<UserDTO>();
		if(currentPage == null && pageSize ==null){
			users=  userRepository.getAllUsers();
		}else{
			if(currentPage == null || pageSize == null){
				if(currentPage == null){
					currentPage = 1;
				}else{
					pageSize = 10;
				}
			}
			users= userRepository.getPagedUsers(currentPage, pageSize);
		}
		for(UserEntity userEntity: users){
			userDTOList.add(convertToDTO(userEntity));
		}
		return userDTOList;
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
	
	public SessionInfoDTO validateCreditial(String authValue){
		String creditial = authValue.split(" ")[1];
		String userNamePassword = new String(Base64.decodeBase64(creditial));
		
		String[] userNamePasswordArray = userNamePassword.split(":");
		String userName = userNamePasswordArray[0];
		String password = userNamePasswordArray[1];
		
		UserEntity user = userRepository.getUserByName(userName);
		if(user!= null){
			String currentEncrptPassword = DigestUtils.sha1Hex(password);
			if(user.getPassword().equals(currentEncrptPassword)){
				String token = UUID.randomUUID().toString();
				SessionInfoDTO sessionInfoDTO = new SessionInfoDTO();
				sessionInfoDTO.setToken(token);
				
				tokenMap.put(token, new Date());
				return sessionInfoDTO;
			}else{
				throw new LoginFailedException();
			}
		}else{
			throw new LoginFailedException();
		}
	}

	public boolean validateToken(String token){
		if(tokenMap.containsKey(token)){
			return true;
		}else{
			return false;
		}
	}
	
	public void updateTokenTime(String token){
		tokenMap.put(token, new Date());
	}
}
