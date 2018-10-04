package com.nexiilabs.autolifecycle.usermanagement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<UserModel> getAll() {
		// TODO Auto-generated method stub
		return userRepository.getAll();
	}

}
