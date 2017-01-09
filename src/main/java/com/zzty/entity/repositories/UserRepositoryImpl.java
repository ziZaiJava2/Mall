package com.zzty.entity.repositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zzty.entity.AddressEntity;
import com.zzty.entity.UserEntity;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

	@PersistenceContext
	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public UserEntity getUser(Long id) {
		return em.find(UserEntity.class, id);
	}

	@Override
	public UserEntity createUser(UserEntity userEntity) {
		em.persist(userEntity);
		return userEntity;
	}

	@Override
	public AddressEntity createAddress(AddressEntity addressEntity) {
		em.persist(addressEntity);
		return addressEntity;
	} 
}
