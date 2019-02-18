package com.jdp.irs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jdp.irs.entity.UserEntity;
import com.jdp.irs.exception.UserNotFoundException;
import com.jdp.irs.model.User;
import com.jdp.irs.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public User getUserDetails(String userId) throws UserNotFoundException {
//		UserEntity ue = userRepository.findOne(userId);
		UserEntity ue = userRepository.getOne(userId);
		if (ue == null) {
			throw new UserNotFoundException("UserService.USER_NOT_FOUND");
		}
		User user = new User();
		user.setCity(ue.getCity());
		user.setEmail(ue.getEmail());
		user.setName(ue.getName());
		user.setPhone(ue.getPhone());
		user.setUserId(ue.getUserId());
		return user;
	}
}
