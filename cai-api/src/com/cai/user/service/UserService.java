package com.cai.user.service;

import com.cai.service.base.GenericDao;
import com.cai.user.domains.User;

public interface UserService extends GenericDao<User, Integer> {
	public User findUser(String name);
}
