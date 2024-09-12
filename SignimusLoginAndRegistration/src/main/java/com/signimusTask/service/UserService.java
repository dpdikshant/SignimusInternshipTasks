package com.signimusTask.service;

import com.signimusTask.entity.User;

public interface UserService {
	
	public User saveUser(User user);

	public void removeSessionMessage();

}
