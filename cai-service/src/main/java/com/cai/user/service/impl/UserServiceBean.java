package com.cai.user.service.impl;

import com.cai.service.base.GenericDaoImpl;
import com.cai.user.domains.User;
import com.cai.user.service.UserService;

public class UserServiceBean extends GenericDaoImpl<User, Integer>implements UserService {

	public User findUser(String name) {
		User user = (User) em.createQuery("select o from User o where o.name=?1").setParameter(1, name)
				.getSingleResult();
		return user;
	}

	

}
