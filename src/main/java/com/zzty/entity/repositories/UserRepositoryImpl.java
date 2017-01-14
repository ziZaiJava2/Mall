package com.zzty.entity.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
	
	@Override
	public List<UserEntity> getAllUsers(){
		return em.createQuery("select u from UserEntity u", UserEntity.class).getResultList();
	}
	
	@Override
	public List<UserEntity> getPagedUsers(Integer currentPage, Integer pageSize ){
		Query query = em.createQuery("select u from UserEntity u", UserEntity.class);
		query.setFirstResult((currentPage-1)*pageSize);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}
	
	@Override
	public UserEntity getUserByName(String userName){
		Query query = em.createQuery("select u from UserEntity u where u.nickName='"+userName+"'", UserEntity.class);
		List<UserEntity> result = query.getResultList();
		if(result == null){
			return null;
		}else{
			return result.get(0);
		}
	}
}
