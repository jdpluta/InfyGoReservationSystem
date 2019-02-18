/**
 * 
 */
package com.jdp.irs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jdp.irs.entity.UserEntity;
import com.jdp.irs.exception.InvalidCredentialException;
import com.jdp.irs.model.Login;
import com.jdp.irs.model.User;
import com.jdp.irs.repository.UserRepository;

@Service
public class LoginService {
	@Autowired
	private UserRepository userRepository;

	public User authenticateLogin(Login userLogin) throws InvalidCredentialException {
//		UserEntity userentity = userRepository.findOne(userLogin.getUserName());
		UserEntity userentity = userRepository.getOne(userLogin.getUserName());
		User user = new User();
		user.setCity(userentity.getCity());
		user.setEmail(userentity.getEmail());
		user.setName(userentity.getName());
		user.setPassword(userentity.getPassword());
		user.setPhone(userentity.getPhone());
		user.setUserId(userentity.getUserId());
		if (user == null) {
			throw new InvalidCredentialException("LoginService.INVALID_CREDENTIALS");
		}
		else if (!(user.getPassword().equals(userLogin.getPassword()))) {
			throw new InvalidCredentialException("LoginService.INVALID_CREDENTIALS");
		}
		return user;
	}
}
